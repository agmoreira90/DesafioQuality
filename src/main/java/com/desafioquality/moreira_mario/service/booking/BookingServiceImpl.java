package com.desafioquality.moreira_mario.service.booking;

import com.desafioquality.moreira_mario.dtos.*;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.hotel.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingServiceImpl implements BookingService {

    private HotelService hotelService;

    public BookingServiceImpl(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * Executes the Booking
     *
     * @param bookingRequestDTO the request dto for the booking
     * @return a BookingResponseDTO after complete the booking
     * This method create a BookingResponseDTO to be return with a StatusCodeDTO
     */
    @Override
    public BookingResponseDTO booking(BookingRequestDTO bookingRequestDTO) throws ApiException {
        BookingResponseDTO response = new BookingResponseDTO();
        response.setUserName(bookingRequestDTO.getUserName());
        response = this.loadBooking(response, bookingRequestDTO.getBooking());
        String cardType = bookingRequestDTO.getBooking().getPaymentMethod().getType();
        Integer dues = bookingRequestDTO.getBooking().getPaymentMethod().getDues();
        HotelDTO hotel = this.validateHotel(bookingRequestDTO);
        response = calculateTotal(response, cardType, dues, hotel);
        StatusCodeDTO statusCode = new StatusCodeDTO();
        statusCode.setCode(HttpStatus.OK.value());
        statusCode.setMessage("El proceso termino satisfactoriamente");
        response.setStatusCode(statusCode);
        return response;
    }
    /**
     * Executes Dates filtering
     *
     * @param dateFrom reservation start date
     * @param dateTo reservation end date
     * @param destination reservation destination
     * @param hotelCode hotel code
     * @return a Hotel dto
     * This method validate the dates loaded by the user at the BookingRequest
     */
    private HotelDTO validateDates(String dateFrom, String dateTo, String destination, String hotelCode) throws ApiException {

        Map<String, String> filter = new HashMap<>();
        filter.put("availabilityFrom", dateFrom);
        filter.put("availabilityTo", dateTo);
        this.hotelService.getHotels(filter);
        HotelDTO hotel = this.validateCity(dateFrom, dateTo, destination, hotelCode);
        return hotel;
    }
    /**
     * Executes City filtering
     *
     * @param dateFrom reservation start date
     * @param dateTo reservation end date
     * @param destination reservation destination
     * @param hotelCode hotel code
     * @return a Hotel dto
     * This method validate the dates and city loaded by the user at the BookingRequest
     */
    private HotelDTO validateCity(String dateFrom, String dateTo, String destination, String hotelCode) throws ApiException {
        Map<String, String> filter = new HashMap<>();
        filter.put("availabilityFrom", dateFrom);
        filter.put("availabilityTo", dateTo);
        filter.put("city", destination);
        this.hotelService.getHotels(filter);
        HotelDTO hotel = this.validateHotelCode(dateFrom, dateTo, destination, hotelCode);
        return hotel;
    }
    /**
     * Executes HotelCode filtering
     *
     * @param dateFrom reservation start date
     * @param dateTo reservation end date
     * @param destination reservation destination
     * @param hotelCode hotel code
     * @return a Hotel dto
     * This method validate the dates, the city and hotel code loaded by the user at the BookingRequest
     */
    private HotelDTO validateHotelCode(String dateFrom, String dateTo, String destination, String hotelCode) throws ApiException {
        Map<String, String> filter = new HashMap<>();
        filter.put("availabilityFrom", dateFrom);
        filter.put("availabilityTo", dateTo);
        filter.put("city", destination);
        filter.put("hotelCode", hotelCode);
        List<HotelDTO> hotels = hotelService.getHotels(filter);
        if (hotels.size() == 1) {
            HotelDTO hotel = hotels.get(0);
            return hotel;
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El destino elegido no existe.");
        }
    }
    /**
     * Calculate the Interest by Card Type
     *
     * @param response ResponseBookingDTO
     * @param cardType the type of card entered by the user
     * @param dues quantity of dues entered
     * @param hotel hotel dto loaded with the filters entered by the user
     * @return a ResponseBookingDTO
     * This method validate the card type entered and set the respective interest depending by the dues
     */
    private BookingResponseDTO calculateTotal(BookingResponseDTO response, String cardType, Integer dues, HotelDTO hotel) {
        if (cardType.equals("CREDIT")) {
            switch (dues) {
                case 1:
                case 2:
                case 3:
                    response = setPrice(response, hotel, 5D);
                    break;
                case 4:
                case 5:
                case 6:
                    response = setPrice(response, hotel, 10D);
                    break;
            }
        }
        if (cardType.equals("DEBIT")) {
            response = setPrice(response, hotel, 0D);
        }
        return response;
    }
    /**
     * Set the Price values for the Reservation
     *
     * @param response ResponseBookingDTO
     * @param hotel hotel dto loaded with the filters entered by the user
     * @param interest the interest value
     * @return a ResponseBookingDTO
     * This method set the amount, the interest and total of the reservation
     */
    private BookingResponseDTO setPrice(BookingResponseDTO response, HotelDTO hotel, Double interest) {
        response.setAmount(hotel.getPrice());
        response.setInterest(interest);
        Double total = response.getAmount() + (response.getAmount() * (interest / 100));
        response.setTotal(total);
        return response;
    }
    /**
     * Set the Response BookingDto
     *
     * @param response ResponseBookingDTO
     * @param booking id the booking entered by the user to make the reservation
     * @return a ResponseBookingDTO
     * This method set the Booking to the BookingResponseDTO from BookingInDTO
     */
    private BookingResponseDTO loadBooking(BookingResponseDTO response, BookingInDTO booking) throws ApiException {
        BookingOutDTO bookingOut = new BookingOutDTO();
        bookingOut.setRoomType(booking.getRoomType());
        bookingOut.setDestination(booking.getDestination());
        bookingOut.setHotelCode(booking.getHotelCode());
        bookingOut.setPeople(booking.getPeople());
        bookingOut.setDateFrom(booking.getDateFrom());
        bookingOut.setDateTo(booking.getDateTo());
        bookingOut.setPeopleAmount(booking.getPeopleAmount());
        response.setBooking(bookingOut);
        return response;
    }
    /**
     * Validate Hotel
     *
     * @param bookingRequestDTO BookingRequestDTO
     * @return a HotelDto
     * This method search the BookingRequestDTO's hotel in the database and if exist update it to Reserved status
     */
    private HotelDTO validateHotel(BookingRequestDTO bookingRequestDTO) throws ApiException {
        String dateFrom = bookingRequestDTO.getBooking().getDateFrom();
        String dateTo = bookingRequestDTO.getBooking().getDateFrom();
        String destination = bookingRequestDTO.getBooking().getDestination();
        String hotelCode = bookingRequestDTO.getBooking().getHotelCode();
        HotelDTO hotel = this.validateDates(dateFrom, dateTo, destination, hotelCode);
        this.hotelService.updateHotel(hotel);
        return hotel;
    }
}
