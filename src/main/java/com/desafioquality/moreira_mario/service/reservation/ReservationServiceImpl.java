package com.desafioquality.moreira_mario.service.reservation;

import com.desafioquality.moreira_mario.dtos.*;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.flight.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {
    private FlightService flightService;

    public ReservationServiceImpl(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public FlightReservationResponseDTO reservate(FlightReservationRequestDTO reservationRequest) throws ApiException {
        FlightReservationResponseDTO response = new FlightReservationResponseDTO();
        response.setUserName(reservationRequest.getUserName());
        response = this.loadReservation(response, reservationRequest.getReservation());
        String cardType = reservationRequest.getReservation().getPaymentMethod().getType();
        Integer dues = reservationRequest.getReservation().getPaymentMethod().getDues();
        FlightDTO flight = this.validateFlight(reservationRequest);
        response = calculateTotal(response, cardType, dues, flight);
        StatusCodeDTO statusCode = new StatusCodeDTO();
        statusCode.setCode(HttpStatus.OK.value());
        statusCode.setMessage("El proceso termino satisfactoriamente");
        response.setStatusCode(statusCode);
        return response;
    }

    /**
     * Set the Response ReservationDTO
     *
     * @param response    ReservationDTO
     * @param reservation reservation entered by the user
     * @return a ReservationDTO
     * This method set the Reservation to the ReservationResponseDTO from ReservationInDTO
     */
    private FlightReservationResponseDTO loadReservation(FlightReservationResponseDTO response, FlightReservationInDTO reservation) throws ApiException {
        try {
            FlightReservationOutDTO reservationOut = new FlightReservationOutDTO();
            reservationOut.setSeatType(reservation.getSeatType());
            reservationOut.setSeats(reservation.getSeats());
            reservationOut.setFlightNumber(reservation.getFlightNumber());
            reservationOut.setDestination(reservation.getDestination());
            reservationOut.setOrigin(reservation.getOrigin());
            reservationOut.setDateTo(reservation.getDateTo());
            reservationOut.setDateFrom(reservation.getDateFrom());
            reservationOut.setPeople(reservation.getPeople());
            response.setReservation(reservationOut);
            return response;
        } catch (NullPointerException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: Formato de JSON invalido.");
        }

    }

    /**
     * Validate Flight
     *
     * @param reservationRequest ReservationRequestDTO
     * @return a FlightDto
     * This method search the ReservationRequestDTO's flight in the database
     */
    private FlightDTO validateFlight(FlightReservationRequestDTO reservationRequest) throws ApiException {
        String dateFrom = reservationRequest.getReservation().getDateFrom();
        String dateTo = reservationRequest.getReservation().getDateTo();
        String destination = reservationRequest.getReservation().getDestination();
        String origin = reservationRequest.getReservation().getOrigin();
        String flightNumbre = reservationRequest.getReservation().getFlightNumber().toString();
        FlightDTO flight = this.validateDates(dateFrom, dateTo, destination, origin, flightNumbre);
        return flight;
    }

    /**
     * Executes Dates filtering
     *
     * @param dateFrom     reservation start date
     * @param dateTo       reservation end date
     * @param destination  reservation destination
     * @param origin       reservation origin
     * @param flightNumbre reservation flight number
     * @return a Flight dto
     * This method validate the dates loaded by the user at the ReservationRequest
     */
    private FlightDTO validateDates(String dateFrom, String dateTo, String destination, String origin, String flightNumbre) throws ApiException {

        Map<String, String> filter = new HashMap<>();
        filter.put("flightDeparture", dateFrom);
        filter.put("flightArrival", dateTo);
        this.flightService.getFlights(filter);
        FlightDTO flight = this.validateOrigin(dateFrom, dateTo, destination, origin, flightNumbre);
        return flight;
    }

    /**
     * Executes Origin filtering
     *
     * @param dateFrom     reservation start date
     * @param dateTo       reservation end date
     * @param destination  reservation destination
     * @param origin       reservation origin
     * @param flightNumbre reservation flight number
     * @return a Flight dto
     * This method validate the dates and origin loaded by the user at the ReservationRequest
     */
    private FlightDTO validateOrigin(String dateFrom, String dateTo, String destination, String origin, String flightNumbre) throws ApiException {
        Map<String, String> filter = new HashMap<>();
        filter.put("flightDeparture", dateFrom);
        filter.put("flightArrival", dateTo);
        filter.put("flightOrigin", origin);
        this.flightService.getFlights(filter);
        FlightDTO flight = this.validateDestination(dateFrom, dateTo, destination, origin, flightNumbre);
        return flight;
    }

    /**
     * Executes Destination filtering
     *
     * @param dateFrom     reservation start date
     * @param dateTo       reservation end date
     * @param destination  reservation destination
     * @param origin       reservation origin
     * @param flightNumbre reservation flight number
     * @return a Flight dto
     * This method validate the dates, origin and destination loaded by the user at the ReservationRequest
     */
    private FlightDTO validateDestination(String dateFrom, String dateTo, String destination, String origin, String flightNumbre) throws ApiException {
        Map<String, String> filter = new HashMap<>();
        filter.put("flightDeparture", dateFrom);
        filter.put("flightArrival", dateTo);
        filter.put("flightOrigin", origin);
        filter.put("flightDestination", destination);
        List<FlightDTO> flights = this.flightService.getFlights(filter);
        if (flights.size() >= 1) {
            FlightDTO flight = validateFlightNumber(dateFrom, dateTo, destination, origin, flightNumbre);
            return flight;
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El destino elegido no existe.");
        }
    }

    /**
     * Executes Flight Number filtering
     *
     * @param dateFrom     reservation start date
     * @param dateTo       reservation end date
     * @param destination  reservation destination
     * @param origin       reservation origin
     * @param flightNumbre reservation flight number
     * @return a Flight dto
     * This method validate the dates, origin, destination and flight number loaded by the user at the ReservationRequest
     */
    private FlightDTO validateFlightNumber(String dateFrom, String dateTo, String destination, String origin, String flightNumbre) throws ApiException {
        Map<String, String> filter = new HashMap<>();
        filter.put("flightDeparture", dateFrom);
        filter.put("flightArrival", dateTo);
        filter.put("flightOrigin", origin);
        filter.put("flightDestination", destination);
        filter.put("flightNumber", flightNumbre);
        List<FlightDTO> flights = this.flightService.getFlights(filter);
        if (flights.size() == 1) {
            FlightDTO flight = this.flightService.getFlights(filter).get(0);
            return flight;
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El Vuelo elegido no existe.");
        }
    }

    /**
     * Calculate the Interest by Card Type
     *
     * @param response ResponseBookingDTO
     * @param cardType the type of card entered by the user
     * @param dues     quantity of dues entered
     * @param flight   flight dto loaded with the filters entered by the user
     * @return a ReservationResponseDTO
     * This method validate the card type entered and set the respective interest depending by the dues
     */
    private FlightReservationResponseDTO calculateTotal(FlightReservationResponseDTO response, String cardType, Integer dues, FlightDTO flight) {
        if (cardType.equals("CREDIT")) {
            switch (dues) {
                case 1:
                case 2:
                case 3:
                    response = setPrice(response, flight, 5D);
                    break;
                case 4:
                case 5:
                case 6:
                    response = setPrice(response, flight, 10D);
                    break;
            }
        }
        if (cardType.equals("DEBIT")) {
            response = setPrice(response, flight, 0D);
        }
        return response;
    }

    /**
     * Set the Price values for the Reservation
     *
     * @param response ResponseBookingDTO
     * @param flight   flight dto loaded with the filters entered by the user
     * @param interest the interest value
     * @return a ReservationResponseDTO
     * This method set the amount, the interest and total of the reservation
     */
    private FlightReservationResponseDTO setPrice(FlightReservationResponseDTO response, FlightDTO flight, Double interest) {
        response.setAmount(flight.getPrice());
        response.setInterest(interest);
        Double total = response.getAmount() + (response.getAmount() * (interest / 100));
        response.setTotal(total);
        return response;
    }
}
