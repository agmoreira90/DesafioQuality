package com.desafioquality.moreira_mario.controllers.reservation;

import com.desafioquality.moreira_mario.dtos.BookingRequestDTO;
import com.desafioquality.moreira_mario.dtos.BookingResponseDTO;
import com.desafioquality.moreira_mario.dtos.FlightReservationRequestDTO;
import com.desafioquality.moreira_mario.dtos.FlightReservationResponseDTO;
import com.desafioquality.moreira_mario.exceptions.ApiError;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.repositories.flight.FlightRepository;
import com.desafioquality.moreira_mario.repositories.flight.FlightRepositoryImpl;
import com.desafioquality.moreira_mario.service.flight.FlightService;
import com.desafioquality.moreira_mario.service.flight.FlightServiceImpl;
import com.desafioquality.moreira_mario.service.reservation.ReservationService;
import com.desafioquality.moreira_mario.service.reservation.ReservationServiceImpl;
import com.desafioquality.moreira_mario.utils.UtilTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ReservationContrellerIntegralTest {
    private ReservationContreller reservationContreller;

    private ReservationService reservationService;

    private FlightService flightService;

    private FlightRepository flightRepository;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private SimpleDateFormat sdf;

    @BeforeEach
    public void setup() throws IOException {
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);

        flightRepository = new FlightRepositoryImpl("classpath:AgencyTest.properties", "flightSheet");
        flightService = new FlightServiceImpl(flightRepository);
        reservationService = new ReservationServiceImpl(flightService);
        reservationContreller = new ReservationContreller(reservationService);

        mockMvc = MockMvcBuilders.standaloneSetup(reservationContreller)
                .build();

        mapper = new ObjectMapper().findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void flightReservation() throws Exception {
        FlightReservationRequestDTO reservationRequest = UtilTest.createReservationRequest("CREDIT",2);
        FlightReservationResponseDTO miFlightReservationResponse = UtilTest.createReservationResponse("CREDIT",2);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/flight-reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertNotNull(resultJson);
        FlightReservationResponseDTO reservationResponse = mapper.readValue(resultJson, FlightReservationResponseDTO.class);
        assertEquals(miFlightReservationResponse, reservationResponse);
    }

    @Test
    public void flightReservationException() throws Exception {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Error: Formato de JSON invalido.";
        String status = HttpStatus.BAD_REQUEST.name();
        ApiError apiError = new ApiError(status, message, statusCode);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/flight-reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertNotNull(resultJson);
        ApiError reservationError = mapper.readValue(resultJson, ApiError.class);
        assertEquals(apiError.toString(), reservationError.toString());
    }
}