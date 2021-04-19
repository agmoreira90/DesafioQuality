package com.desafioquality.moreira_mario.controllers.reservation;

import com.desafioquality.moreira_mario.dtos.FlightDTO;
import com.desafioquality.moreira_mario.dtos.FlightReservationRequestDTO;
import com.desafioquality.moreira_mario.dtos.FlightReservationResponseDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.flight.FlightService;
import com.desafioquality.moreira_mario.service.reservation.ReservationService;
import com.desafioquality.moreira_mario.service.reservation.ReservationServiceImpl;
import com.desafioquality.moreira_mario.utils.UtilTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;

class ReservationContrellerTest {
    private ReservationContreller reservationContreller;

    private ReservationService reservationService;

    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        reservationService = mock(ReservationServiceImpl.class);
        this.reservationContreller = new ReservationContreller(reservationService);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
    }

    @Test
    void reservateCreditA() throws ApiException, ParseException {
        FlightReservationRequestDTO reservationRequest = UtilTest.createReservationRequest("CREDIT",2);
        FlightReservationResponseDTO miFlightReservationResponse = UtilTest.createReservationResponse("CREDIT",2);
        Mockito.when(reservationService.reservate(reservationRequest)).thenReturn(miFlightReservationResponse);
        FlightReservationResponseDTO flightReservationResponse = this.reservationService.reservate(reservationRequest);
        Mockito.verify(reservationService,atLeast(1)).reservate(reservationRequest);
        assertEquals(miFlightReservationResponse,flightReservationResponse);
    }

    @Test
    void reservateCreditB() throws ApiException, ParseException {
        FlightReservationRequestDTO reservationRequest = UtilTest.createReservationRequest("CREDIT",4);
        FlightReservationResponseDTO miFlightReservationResponse = UtilTest.createReservationResponse("CREDIT",4);
        Mockito.when(reservationService.reservate(reservationRequest)).thenReturn(miFlightReservationResponse);
        FlightReservationResponseDTO flightReservationResponse = this.reservationService.reservate(reservationRequest);
        Mockito.verify(reservationService,atLeast(1)).reservate(reservationRequest);
        assertEquals(miFlightReservationResponse,flightReservationResponse);
    }
    @Test
    void reservateDebit() throws ApiException, ParseException {
        FlightReservationRequestDTO reservationRequest = UtilTest.createReservationRequest("DEBIT",1);
        FlightReservationResponseDTO miFlightReservationResponse = UtilTest.createReservationResponse("DEBIT",1);
        Mockito.when(reservationService.reservate(reservationRequest)).thenReturn(miFlightReservationResponse);
        FlightReservationResponseDTO flightReservationResponse = this.reservationService.reservate(reservationRequest);
        Mockito.verify(reservationService,atLeast(1)).reservate(reservationRequest);
        assertEquals(miFlightReservationResponse,flightReservationResponse);
    }

    @Test
    void validateFlightException() throws ApiException, ParseException {
        FlightReservationRequestDTO flightReservationRequest = UtilTest.createReservationRequest("DEBIT",1);
        Mockito.when(reservationService.reservate(flightReservationRequest)).thenThrow(new ApiException(HttpStatus.BAD_REQUEST, "Error: El destino elegido no existe."));
        String message = assertThrows(ApiException.class,()->this.reservationContreller.reservate(flightReservationRequest)).getMessage();
        assertEquals("Error: El destino elegido no existe.", message);
    }
}