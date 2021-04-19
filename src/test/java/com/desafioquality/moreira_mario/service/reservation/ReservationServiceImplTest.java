package com.desafioquality.moreira_mario.service.reservation;

import com.desafioquality.moreira_mario.dtos.*;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.booking.BookingService;
import com.desafioquality.moreira_mario.service.booking.BookingServiceImpl;
import com.desafioquality.moreira_mario.service.flight.FlightService;
import com.desafioquality.moreira_mario.service.hotel.HotelService;
import com.desafioquality.moreira_mario.utils.UtilTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;

class ReservationServiceImplTest {
    private ReservationService reservationService;

    private FlightService flightService;

    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        flightService = mock(FlightService.class);
        this.reservationService = new ReservationServiceImpl(flightService);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
    }

    @Test
    void reservateCreditA() throws ApiException, ParseException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add( new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", 6500D, sdf.parse("10/02/2021"), sdf.parse("15/02/2021")));
        Mockito.when(flightService.getFlights(Mockito.any())).thenReturn(flights);
        FlightReservationRequestDTO reservationRequest = UtilTest.createReservationRequest("CREDIT",2);
        FlightReservationResponseDTO flightReservationResponse = this.reservationService.reservate(reservationRequest);
        FlightReservationResponseDTO miFlightReservationResponse = UtilTest.createReservationResponse("CREDIT",2);
        Mockito.verify(flightService,atLeast(5)).getFlights(Mockito.any());
        assertEquals(miFlightReservationResponse,flightReservationResponse);


    }

    @Test
    void reservateCreditB() throws ApiException, ParseException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add( new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", 6500D, sdf.parse("10/02/2021"), sdf.parse("15/02/2021")));
        Mockito.when(flightService.getFlights(Mockito.any())).thenReturn(flights);
        FlightReservationRequestDTO reservationRequest = UtilTest.createReservationRequest("CREDIT",4);
        FlightReservationResponseDTO flightReservationResponse = this.reservationService.reservate(reservationRequest);
        FlightReservationResponseDTO miFlightReservationResponse = UtilTest.createReservationResponse("CREDIT",4);
        Mockito.verify(flightService,atLeast(5)).getFlights(Mockito.any());
        assertEquals(miFlightReservationResponse,flightReservationResponse);


    }
    @Test
    void reservateDebit() throws ApiException, ParseException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add( new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", 6500D, sdf.parse("10/02/2021"), sdf.parse("15/02/2021")));
        Mockito.when(flightService.getFlights(Mockito.any())).thenReturn(flights);
        FlightReservationRequestDTO reservationRequest = UtilTest.createReservationRequest("DEBIT",1);
        FlightReservationResponseDTO flightReservationResponse = this.reservationService.reservate(reservationRequest);
        FlightReservationResponseDTO miFlightReservationResponse = UtilTest.createReservationResponse("DEBIT",1);
        Mockito.verify(flightService,atLeast(5)).getFlights(Mockito.any());
        assertEquals(miFlightReservationResponse,flightReservationResponse);
    }

    @Test
    void validateFlightException() throws ApiException, ParseException {
        FlightReservationRequestDTO flightReservationRequest = UtilTest.createReservationRequest("DEBIT",1);
        String message = assertThrows(ApiException.class,()->this.reservationService.reservate(flightReservationRequest)).getMessage();
        assertEquals("Error: El destino elegido no existe.", message);
    }

    @Test
    void validateFlightJSONException() throws ApiException, ParseException {
        FlightReservationRequestDTO flightReservationRequest = UtilTest.createReservationRequest("DEBIT",1);
        flightReservationRequest.setReservation(null);
        String message = assertThrows(ApiException.class,()->this.reservationService.reservate(flightReservationRequest)).getMessage();
        assertEquals("Error: Formato de JSON invalido.", message);
    }

    @Test
    void validateFlightNumberException() throws ApiException, ParseException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add( new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", 6500D, sdf.parse("10/02/2021"), sdf.parse("15/02/2021")));
        FlightReservationRequestDTO flightReservationRequest = UtilTest.createReservationRequest("DEBIT",1);
        flightReservationRequest.getReservation().setFlightNumber("00000");
        Map<String, String> filter = new HashMap<>();
        filter.put("flightDeparture", "10/02/2021");
        filter.put("flightArrival", "15/02/2021");
        filter.put("flightOrigin", "Buenos Aires");
        filter.put("flightDestination", "Puerto Iguazú");
        Mockito.when(flightService.getFlights(filter)).thenReturn(flights);
        Map<String, String> filter2 = new HashMap<>();
        filter2.put("flightDeparture", "10/02/2021");
        filter2.put("flightArrival", "15/02/2021");
        filter2.put("flightOrigin", "Buenos Aires");
        filter2.put("flightDestination", "Puerto Iguazú");
        filter2.put("flightNumber", "BAPI-1235");
        Mockito.when(flightService.getFlights(filter2)).thenReturn(flights);
        String message = assertThrows(ApiException.class,()->this.reservationService.reservate(flightReservationRequest)).getMessage();
        assertEquals("Error: El Vuelo elegido no existe.", message);
    }

}