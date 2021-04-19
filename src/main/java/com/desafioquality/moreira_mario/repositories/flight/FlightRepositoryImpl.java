package com.desafioquality.moreira_mario.repositories.flight;

import com.desafioquality.moreira_mario.dtos.FlightDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.XLSXUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FlightRepositoryImpl implements FlightRepository {

    private String propertyPath;
    private String XLSXSheet;

    public FlightRepositoryImpl(@Value("${propertyPath:classpath:Agency.properties}") String propertyPath, @Value("${propertyPath:flightSheet}") String sheet) {
        this.propertyPath = propertyPath;
        this.XLSXSheet = sheet;
    }

    /**
     * Executes the search
     *
     * @param params all the filters
     * @return a filtered map with flight dto
     */
    @Override
    public Map<String, FlightDTO> selectFlight(Map<String, String> params) throws ApiException {
        Map<String, FlightDTO> flights = new HashMap<>();
        Map<Long, ArrayList<Cell>> data = XLSXUtil.readXLSX(this.propertyPath, this.XLSXSheet);
        for (Map.Entry entry : data.entrySet()) {
            List<Cell> line = (ArrayList<Cell>) entry.getValue();
            FlightDTO flight = new FlightDTO();
            flight.setFlightNumber(line.get(0).toString());
            flight.setFlightOrigin(line.get(1).toString());
            flight.setFlightDestination(line.get(2).toString());
            flight.setSeatType(line.get(3).toString());
            String price = line.get(4).toString().replace("$", "");
            price = price.replace(".", "");
            flight.setPrice(Double.parseDouble(price));
            flight = setDates(flight,line);
            flights.put(entry.getKey().toString(), flight);
        }
        flights = applyFilters(params,flights);
        return flights;
    }

    /**
     * Executes the search filtering
     *
     * @param params all the filters selected by the user
     * @param flights all the flights
     * @return a filtered map with flight dto
     */
    private Map<String, FlightDTO> applyFilters(Map<String, String> params, Map<String, FlightDTO> flights) throws ApiException {
        for (Map.Entry<String, String> filter : params.entrySet()) {
            switch (filter.getKey()) {
                case "flightNum":
                    flights = filterFlightNum(flights, filter.getValue());
                    break;
                case "flightOrigin":
                    flights = filterFlightOrigin(flights, filter.getValue());
                    break;
                case "flightDestination":
                    flights = filterFlightDestination(flights, filter.getValue());
                    break;
                case "seatType":
                    flights = filterSeatType(flights, filter.getValue());
                    break;
                case "price":
                    flights = filterPrice(flights, filter.getValue());
                    break;
                case "flightDeparture":
                    flights = this.validateDateFilters(flights, params);
                    break;
                case "flightArrival":
                    flights = this.validateDateFilters(flights, params);
                    break;
            }
        }
        return flights;
    }

    /**
     * Executes the search by Flight Num
     *
     * @param filter have the flightNum used to filter the map
     * @param flights all the flights
     * @return a filtered map with flight dto
     */
    private Map<String, FlightDTO> filterFlightNum(Map<String, FlightDTO> flights, String filter) {
        flights = flights.entrySet().stream()
                .filter(flight -> flight.getValue().getFlightNumber().equals(filter))
                .collect(Collectors.toMap(flight -> flight.getKey(), flight -> flight.getValue()));
        return flights;
    }

    /**
     * Executes the search by Origin
     *
     * @param filter have the flight origin used to filter the map
     * @param flights all the flights
     * @return a filtered map with flight dto
     */
    private Map<String, FlightDTO> filterFlightOrigin(Map<String, FlightDTO> flights, String filter) throws ApiException {
        flights = flights.entrySet().stream()
                .filter(flight -> flight.getValue().getFlightOrigin().equals(filter))
                .collect(Collectors.toMap(flight -> flight.getKey(), flight -> flight.getValue()));
        if (flights.size() == 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El origen elegido no existe.");
        }
        return flights;
    }

    /**
     * Executes the search by Destination
     *
     * @param filter have the flight destination used to filter the map
     * @param flights all the flights
     * @return a filtered map with flights dto
     */
    private Map<String, FlightDTO> filterFlightDestination(Map<String, FlightDTO> flights, String filter) throws ApiException {
        flights = flights.entrySet().stream()
                .filter(flight -> flight.getValue().getFlightDestination().equals(filter))
                .collect(Collectors.toMap(flight -> flight.getKey(), flight -> flight.getValue()));
        if (flights.size() == 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El destino elegido no existe.");
        }
        return flights;
    }

    /**
     * Executes the search by Seat Type
     *
     * @param filter have the seat type used to filter the map
     * @param flights all the flights
     * @return a filtered map with flight dto
     */
    private Map<String, FlightDTO> filterSeatType(Map<String, FlightDTO> flights, String filter) throws ApiException {
        flights = flights.entrySet().stream()
                .filter(flight -> flight.getValue().getSeatType().equals(filter))
                .collect(Collectors.toMap(flight -> flight.getKey(), flight -> flight.getValue()));
        if (flights.size() == 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El tipo de asiento elegido no esta disponible.");
        }
        return flights;
    }

    /**
     * Executes the search by Price
     *
     * @param filter have the price used to filter the map
     * @param flights all the flights
     * @return a filtered map with flight dto
     */
    private Map<String, FlightDTO> filterPrice(Map<String, FlightDTO> flights, String filter) {
        flights = flights.entrySet().stream()
                .filter(flight -> flight.getValue().getPrice() == Double.parseDouble(filter))
                .collect(Collectors.toMap(flight -> flight.getKey(), flight -> flight.getValue()));
        return flights;
    }

    /**
     * Executes the search by Date
     *
     * @param filters it have all the filters given by the user
     * @param flights  all the flights
     * @return a filtered map with flights dto
     * this method validate the date format on the filters, that both were loaded
     * and that the date From be before than the date To
     */
    private Map<String, FlightDTO> validateDateFilters(Map<String, FlightDTO> flights, Map<String, String> filters) throws ApiException {
        if (filters.get("flightDeparture") != null && filters.get("flightArrival") != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                sdf.setLenient(false);
                Date dateFrom = sdf.parse(filters.get("flightDeparture"));
                Date dateTo = sdf.parse(filters.get("flightArrival"));
                if (dateFrom.after(dateTo)) {
                    throw new ApiException(HttpStatus.CONFLICT, "Error: La fecha de vuelta debe ser mayor a la de ida.");
                } else {
                    flights = filterFlightDeparture(flights, filters.get("flightDeparture"));
                    flights = filterFlightArrival(flights, filters.get("flightArrival"));
                }
                return flights;
            } catch (ParseException e) {
                throw new ApiException(HttpStatus.CONFLICT, "Error: Formato de fecha filtros debe ser dd/mm/aaaa.");
            }
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: Debe cargar fecha de entrada y de salida.");
        }
    }

    /**
     * Executes filter by FlightDeparture
     *
     * @param flights all the flights
     * @param filter the date from value
     * @return a filtered map with flight dto
     */
    private Map<String, FlightDTO> filterFlightDeparture(Map<String, FlightDTO> flights, String filter) throws ApiException, ParseException {
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        date = sdf.parse(filter);
        flights = flights.entrySet().stream()
                .filter(flight -> date.compareTo(flight.getValue().getFlightDepartureAsDate()) <= 0)
                .collect(Collectors.toMap(flight -> flight.getKey(), flight -> flight.getValue()));
        return flights;
    }

    /**
     * Executes filter by FlightArrival
     *
     * @param flights all the flights
     * @param filter the date to value
     * @return a filtered map with flight dto
     */
    private Map<String, FlightDTO> filterFlightArrival(Map<String, FlightDTO> flights, String filter) throws ApiException, ParseException {
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        date = sdf.parse(filter);
        flights = flights.entrySet().stream()
                .filter(flight -> date.compareTo(flight.getValue().getFlightArrivalAsDate()) >= 0)
                .collect(Collectors.toMap(flight -> flight.getKey(), flight -> flight.getValue()));
        if (flights.size() > 0) {
            return flights;
        } else {
            throw new ApiException(HttpStatus.OK, "Informacion: No Existen vuelos disponibles para ese rango de fechas.");
        }
    }

    /**
     * Set Date Values
     *
     * @param flight FlightDTO
     * @param line  list of cell read from the xlsx
     * @return FlightDto with dates attributes loaded
     */
    private FlightDTO setDates(FlightDTO flight, List<Cell> line) throws ApiException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            flight.setFlightDeparture(sdf.parse(sdf.format(line.get(5).getDateCellValue())));
            flight.setFlightArrival(sdf.parse(sdf.format(line.get(6).getDateCellValue())));
        } catch (ParseException | IllegalStateException e) {
            throw new ApiException(HttpStatus.CONFLICT, "Error: Formato de Fecha en Vuelo: " + flight.getFlightNumber() + " no valido.");
        }
        return flight;
    }
}
