package com.desafioquality.moreira_mario.controllers.booking;

import com.desafioquality.moreira_mario.controllers.reservation.ReservationContreller;
import com.desafioquality.moreira_mario.dtos.*;
import com.desafioquality.moreira_mario.exceptions.ApiError;
import com.desafioquality.moreira_mario.repositories.flight.FlightRepository;
import com.desafioquality.moreira_mario.repositories.flight.FlightRepositoryImpl;
import com.desafioquality.moreira_mario.repositories.hotel.HotelRepository;
import com.desafioquality.moreira_mario.repositories.hotel.HotelRepositoryImpl;
import com.desafioquality.moreira_mario.service.booking.BookingService;
import com.desafioquality.moreira_mario.service.booking.BookingServiceImpl;
import com.desafioquality.moreira_mario.service.flight.FlightService;
import com.desafioquality.moreira_mario.service.flight.FlightServiceImpl;
import com.desafioquality.moreira_mario.service.hotel.HotelService;
import com.desafioquality.moreira_mario.service.hotel.HotelServiceImpl;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class BookingControllerIntegralTest {
    private BookingController bookingController;

    private BookingService bookingService;

    private HotelService hotelService;

    private HotelRepository hotelRepository;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private SimpleDateFormat sdf;


    @BeforeEach
    public void setup() throws IOException {
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);

        hotelRepository = new HotelRepositoryImpl("classpath:AgencyTest.properties", "UpdateHotel");
        hotelService = new HotelServiceImpl(hotelRepository);
        bookingService = new BookingServiceImpl(hotelService);
        bookingController = new BookingController(bookingService);

        mockMvc = MockMvcBuilders.standaloneSetup(bookingController)
                .build();

        mapper = new ObjectMapper().findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void booking() throws Exception {
        BookingRequestDTO bookingRequestDTO = UtilTest.createRequest("CREDIT",2);
        BookingResponseDTO miookingResponse = UtilTest.createResponse("CREDIT",2);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookingRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertNotNull(resultJson);
        BookingResponseDTO bookingResponse = mapper.readValue(resultJson, BookingResponseDTO.class);
        assertEquals(miookingResponse, bookingResponse);
        HotelDTO hotel = new HotelDTO("BH-0002", "Hotel Bristol 2", "Buenos Aires", "Doble", 7200D, sdf.parse("12/02/2021"), sdf.parse("17/04/2021"), true);
        hotelRepository.updateHotel(hotel);
    }

    @Test
    public void bookingException() throws Exception {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Error: Formato de JSON invalido.";
        String status = HttpStatus.BAD_REQUEST.name();
        ApiError apiError = new ApiError(status, message, statusCode);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/booking")
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