package com.desafioquality.moreira_mario.repositories.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.XLSXUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class HotelRepositoryImpl implements HotelRepository {

    private String propertyPath;
    private String XLSXSheet;

    public HotelRepositoryImpl(@Value("${propertyPath:classpath:Agency.properties}") String propertyPath, @Value("${propertyPath:hotelSheet}") String sheet) {
        this.propertyPath = propertyPath;
        this.XLSXSheet = sheet;
    }

    /**
     * Executes the search
     *
     * @param params all the filters
     * @return a filtered map with hotels dto
     */
    @Override
    public Map<String, HotelDTO> selectHotel(Map<String, String> params) throws ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        Map<Long, ArrayList<Cell>> data = XLSXUtil.readXLSX(this.propertyPath, this.XLSXSheet);
        for (Map.Entry entry : data.entrySet()) {
            List<Cell> line = (ArrayList<Cell>) entry.getValue();
            HotelDTO hotel = new HotelDTO();
            hotel.setHotelCode(line.get(0).toString());
            hotel.setName(line.get(1).toString());
            hotel.setCity(line.get(2).toString());
            hotel.setRoomType(line.get(3).toString());
            String price = line.get(4).toString().replace("$", "");
            price = price.replace(".", "");
            hotel.setPrice(Double.parseDouble(price));
            hotel = setDates(hotel, line);
            if (line.get(7).toString().equals("SI")) {
                hotel.setReserved(true);
            } else {
                hotel.setReserved(false);
            }
            hotels.put(entry.getKey().toString(), hotel);
        }
        params.put("isReserved", "");
        hotels = applyFilters(params, hotels);

        return hotels;
    }

    /**
     * Update a Hotel dto at the xlsx
     *
     * @param hotel hotel dto
     * execute hotel update at the xlsx
     */
    @Override
    public void updateHotel(HotelDTO hotel) throws ApiException {

        Map<String, String> filters = new HashMap<>();
        filters.put("0", hotel.getHotelCode());
        filters.put("5", getStrDateFilter(hotel.getAvailabilityFromAsDate()));
        filters.put("6", getStrDateFilter(hotel.getAvailabilityToAsDate()));
        filters.put("2", hotel.getCity());

        Row hotelRow = XLSXUtil.getRow(filters,this.propertyPath,this.XLSXSheet);
        hotelRow.getCell(0).setCellValue(hotel.getHotelCode());
        hotelRow.getCell(2).setCellValue(hotel.getName());
        hotelRow.getCell(2).setCellValue(hotel.getCity());
        hotelRow.getCell(3).setCellValue(hotel.getRoomType());
        hotelRow.getCell(4).setCellValue("$" + hotel.getPrice().toString().replace(".0", ""));
        hotelRow.getCell(5).setCellValue(hotel.getAvailabilityFromAsDate());
        hotelRow.getCell(6).setCellValue(hotel.getAvailabilityToAsDate());

        if (hotel.isReserved()) {
            hotelRow.getCell(7).setCellValue("NO");
        } else {
            hotelRow.getCell(7).setCellValue("SI");
        }
        XLSXUtil.updateXLSX(hotelRow, filters, this.propertyPath, this.XLSXSheet);
    }

    /**
     * Executes the search filtering
     *
     * @param params all the filters selected by the user
     * @param hotels all the hotels
     * @return a filtered map with hotels dto
     */
    private Map<String, HotelDTO> applyFilters(Map<String, String> params, Map<String, HotelDTO> hotels) throws ApiException {
        for (Map.Entry<String, String> filter : params.entrySet()) {
            switch (filter.getKey()) {
                case "hotelCode":
                    hotels = filterHotelCode(hotels, filter.getValue());
                    break;
                case "name":
                    hotels = filterName(hotels, filter.getValue());
                    break;
                case "city":
                    hotels = filterCity(hotels, filter.getValue());
                    break;
                case "roomType":
                    hotels = filterRoomType(hotels, filter.getValue());
                    break;
                case "price":
                    hotels = filterPrice(hotels, filter.getValue());
                    break;
                case "availabilityFrom":
                    hotels = this.validateDateFilters(hotels, params);
                    break;
                case "availabilityTo":
                    hotels = this.validateDateFilters(hotels, params);
                    break;
            }
        }
        hotels = filterIsReservado(hotels);
        return hotels;
    }

    /**
     * Executes the search by Hotel Code
     *
     * @param filter have the hotelcode used to filter the map
     * @param hotels all the hotels
     * @return a filtered map with hotels dto
     */
    private Map<String, HotelDTO> filterHotelCode(Map<String, HotelDTO> hotels, String filter) {
        hotels = hotels.entrySet().stream()
                .filter(hotel -> hotel.getValue().getHotelCode().equals(filter))
                .collect(Collectors.toMap(hotel -> hotel.getKey(), hotel -> hotel.getValue()));
        return hotels;
    }

    /**
     * Executes the search by Name
     *
     * @param filter have the hotel name used to filter the map
     * @param hotels all the hotels
     * @return a filtered map with hotels dto
     */
    private Map<String, HotelDTO> filterName(Map<String, HotelDTO> hotels, String filter) {
        hotels = hotels.entrySet().stream()
                .filter(hotel -> hotel.getValue().getName().equals(filter))
                .collect(Collectors.toMap(hotel -> hotel.getKey(), hotel -> hotel.getValue()));
        return hotels;
    }

    /**
     * Executes the search by City
     *
     * @param filter have the hotel city used to filter the map
     * @param hotels all the hotels
     * @return a filtered map with hotels dto
     */
    private Map<String, HotelDTO> filterCity(Map<String, HotelDTO> hotels, String filter) throws ApiException {
        hotels = hotels.entrySet().stream()
                .filter(hotel -> hotel.getValue().getCity().equals(filter))
                .collect(Collectors.toMap(hotel -> hotel.getKey(), hotel -> hotel.getValue()));
        if (hotels.size() == 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El destino elegido no existe.");
        }
        return hotels;
    }

    /**
     * Executes the search by Room Type
     *
     * @param filter have the room type used to filter the map
     * @param hotels all the hotels
     * @return a filtered map with hotels dto
     */
    private Map<String, HotelDTO> filterRoomType(Map<String, HotelDTO> hotels, String filter) {
        hotels = hotels.entrySet().stream()
                .filter(hotel -> hotel.getValue().getRoomType().equals(filter))
                .collect(Collectors.toMap(hotel -> hotel.getKey(), hotel -> hotel.getValue()));
        return hotels;
    }

    /**
     * Executes the search by Price
     *
     * @param filter have the price used to filter the map
     * @param hotels all the hotels
     * @return a filtered map with hotels dto
     */
    private Map<String, HotelDTO> filterPrice(Map<String, HotelDTO> hotels, String filter) {
        hotels = hotels.entrySet().stream()
                .filter(hotel -> hotel.getValue().getPrice() == Double.parseDouble(filter))
                .collect(Collectors.toMap(hotel -> hotel.getKey(), hotel -> hotel.getValue()));
        return hotels;
    }

    /**
     * Executes the search by IsReservado
     *
     * @param hotels all the hotels
     * @return a filtered map with hotels dto
     * because the exercise requires it, always use this filter to take only the hotels available
     */
    private Map<String, HotelDTO> filterIsReservado(Map<String, HotelDTO> hotels) throws ApiException {
        hotels = hotels.entrySet().stream()
                .filter(hotel -> hotel.getValue().isReserved() == false)
                .collect(Collectors.toMap(hotel -> hotel.getKey(), hotel -> hotel.getValue()));
        if (hotels.size() == 0) {
            throw new ApiException(HttpStatus.OK, "Informcion: No hay reservas disponibles.");
        }
        return hotels;
    }

    /**
     * Executes the search by DateFilters
     *
     * @param filters it have all the filters given by the user
     * @param hotels all the hotels
     * @return a filtered map with hotels dto
     * this method validate the date format on the filters, that both were loaded
     * and that the date From be before than the date To
     */
    private Map<String, HotelDTO> validateDateFilters(Map<String, HotelDTO> hotels, Map<String, String> filters) throws ApiException {
        if (filters.get("availabilityFrom") != null && filters.get("availabilityTo") != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                sdf.setLenient(false);
                Date dateFrom = sdf.parse(filters.get("availabilityFrom"));
                Date dateTo = sdf.parse(filters.get("availabilityTo"));
                if (dateFrom.after(dateTo)) {
                    throw new ApiException(HttpStatus.CONFLICT, "Error: La fecha de salida debe ser mayor a la de entrada.");
                } else {
                    hotels = filterAvailabilityTo(hotels, filters.get("availabilityTo"));
                    hotels = filterAvailabilityFrom(hotels, filters.get("availabilityFrom"));
                }
                return hotels;
            } catch (ParseException e) {
                throw new ApiException(HttpStatus.CONFLICT, "Error: Formato de fecha filtros debe ser dd/mm/aaaa.");
            }
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: Debe cargar fecha de entrada y de salida.");
        }
    }

    /**
     * Executes filter by AvailabilityFrom
     *
     * @param hotels all the hotels
     * @param filter the date from value
     * @return a filtered map with hotels dto
     */
    private Map<String, HotelDTO> filterAvailabilityFrom(Map<String, HotelDTO> hotels, String filter) throws ApiException, ParseException {
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        date = sdf.parse(filter);
        hotels = hotels.entrySet().stream()
                .filter(hotel -> date.compareTo(hotel.getValue().getAvailabilityFromAsDate()) >= 0)
                .collect(Collectors.toMap(hotel -> hotel.getKey(), hotel -> hotel.getValue()));
        return hotels;
    }

    /**
     * Executes filter by AvailabilityTo
     *
     * @param hotels all the hotels
     * @param filter the date to value
     * @return a filtered map with hotels dto
     */
    private Map<String, HotelDTO> filterAvailabilityTo(Map<String, HotelDTO> hotels, String filter) throws ApiException, ParseException {
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        date = sdf.parse(filter);
        hotels = hotels.entrySet().stream()
                .filter(hotel -> date.compareTo(hotel.getValue().getAvailabilityToAsDate()) <= 0)
                .collect(Collectors.toMap(hotel -> hotel.getKey(), hotel -> hotel.getValue()));
        if (hotels.size() > 0) {
            return hotels;
        } else {
            throw new ApiException(HttpStatus.OK, "Informacion: No Existen Hoteles disponibles para ese rango de fechas.");
        }
    }

    /**
     * Convert date to String
     *
     * @param date date value
     * @return a String with  specific date format to compare with the date values where in the xlsx
     */
    private String getStrDateFilter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer dia = calendar.get(Calendar.DAY_OF_MONTH);
        Month mes = Month.of(calendar.get(Calendar.MONTH) + 1);
        Integer ano = calendar.get(Calendar.YEAR);
        String strDate = dia + "-" + mes.getDisplayName(TextStyle.SHORT, new Locale("es", "ES")) + "-" + ano;
        return strDate;
    }

    /**
     * Set Date Values
     *
     * @param hotel HotelDTO
     * @param line  list of cell read from the xlsx
     * @return hotelDTO with dates attributes loaded
     */
    private HotelDTO setDates(HotelDTO hotel, List<Cell> line) throws ApiException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            hotel.setAvailabilityFrom(sdf.parse(sdf.format(line.get(5).getDateCellValue())));
            hotel.setAvailabilityTo(sdf.parse(sdf.format(line.get(6).getDateCellValue())));
        } catch (ParseException | IllegalStateException e) {
            throw new ApiException(HttpStatus.CONFLICT, "Error: Formato de Fecha en Hotel: " + hotel.getName() + " no valido.");
        }
        return hotel;
    }
}
