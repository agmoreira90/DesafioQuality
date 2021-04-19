package com.desafioquality.moreira_mario.service.booking;

import com.desafioquality.moreira_mario.dtos.*;
import com.desafioquality.moreira_mario.exceptions.ApiException;
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

class BookingServiceImplTest {

    private BookingService bookingService;

    private HotelService hotelService;

    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        hotelService = mock(HotelService.class);
        this.bookingService = new BookingServiceImpl(hotelService);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
    }

    @Test
    void bookingCreditA() throws ApiException, ParseException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("BH-0002", "Hotel Bristol 2", "Buenos Aires", "Doble", 7200D, sdf.parse("12/02/2021"), sdf.parse("17/04/2021"), false));
        Mockito.when(hotelService.getHotels(Mockito.any())).thenReturn(hotels);
        BookingRequestDTO bookingRequest = UtilTest.createRequest("CREDIT",2);
        BookingResponseDTO bookingResponse = this.bookingService.booking(bookingRequest);
        BookingResponseDTO miBookingResponse = UtilTest.createResponse("CREDIT",2);
        Mockito.verify(hotelService,atLeast(3)).getHotels(Mockito.any());
        assertEquals(miBookingResponse,bookingResponse);


    }

    @Test
    void bookingCreditB() throws ApiException, ParseException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("BH-0002", "Hotel Bristol 2", "Buenos Aires", "Doble", 7200D, sdf.parse("12/02/2021"), sdf.parse("17/04/2021"), false));
        Mockito.when(hotelService.getHotels(Mockito.any())).thenReturn(hotels);
        BookingRequestDTO bookingRequest = UtilTest.createRequest("CREDIT",5);
        BookingResponseDTO bookingResponse = this.bookingService.booking(bookingRequest);
        BookingResponseDTO miBookingResponse = UtilTest.createResponse("CREDIT",5);
        Mockito.verify(hotelService,atLeast(3)).getHotels(Mockito.any());
        assertEquals(miBookingResponse,bookingResponse);


    }
    @Test
    void bookingDebit() throws ApiException, ParseException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("BH-0002", "Hotel Bristol 2", "Buenos Aires", "Doble", 7200D, sdf.parse("12/02/2021"), sdf.parse("17/04/2021"), false));
        Mockito.when(hotelService.getHotels(Mockito.any())).thenReturn(hotels);
        BookingRequestDTO bookingRequest = UtilTest.createRequest("DEBIT",1);
        BookingResponseDTO bookingResponse = this.bookingService.booking(bookingRequest);
        BookingResponseDTO miBookingResponse = UtilTest.createResponse("DEBIT",1);
        Mockito.verify(hotelService,atLeast(3)).getHotels(Mockito.any());
        assertEquals(miBookingResponse,bookingResponse);
    }

    @Test
    void validateHotelException() throws ApiException, ParseException {
        BookingRequestDTO bookingRequest = UtilTest.createRequest("DEBIT",1);
        String message = assertThrows(ApiException.class,()->this.bookingService.booking(bookingRequest)).getMessage();
        assertEquals("Error: El destino elegido no existe.", message);
    }
    @Test
    void validateFlightJSONException() throws ApiException, ParseException {
        BookingRequestDTO bookingRequest = UtilTest.createRequest("DEBIT",1);
        bookingRequest.setBooking(null);
        String message = assertThrows(ApiException.class,()->this.bookingService.booking(bookingRequest)).getMessage();
        assertEquals("Error: Formato de JSON invalido.", message);
    }
}