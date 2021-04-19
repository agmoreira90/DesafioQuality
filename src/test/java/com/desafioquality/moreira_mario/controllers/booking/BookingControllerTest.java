package com.desafioquality.moreira_mario.controllers.booking;

import com.desafioquality.moreira_mario.dtos.*;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.booking.BookingService;
import com.desafioquality.moreira_mario.utils.UtilTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;

class BookingControllerTest {
    private BookingController bookingController;

    private BookingService bookingService;
    private SimpleDateFormat sdf;
    @BeforeEach
    void setUp() {
        bookingService = mock(BookingService.class);
        this.bookingController = new BookingController(bookingService);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
    }

    @Test
    void bookingCreditA() throws ApiException, ParseException {
        BookingRequestDTO bookingRequest = UtilTest.createRequest("CREDIT",2);
        BookingResponseDTO miBookingResponse = UtilTest.createResponse("CREDIT",2);
        Mockito.when(bookingService.booking(bookingRequest)).thenReturn(miBookingResponse);
        BookingResponseDTO bookingResponse = this.bookingController.booking(bookingRequest);
        Mockito.verify(bookingService,atLeast(1)).booking(bookingRequest);
        assertEquals(miBookingResponse,bookingResponse);
    }

    @Test
    void bookingCreditB() throws ApiException, ParseException {
        BookingRequestDTO bookingRequest = UtilTest.createRequest("CREDIT",5);
        BookingResponseDTO miBookingResponse = UtilTest.createResponse("CREDIT",5);
        Mockito.when(bookingService.booking(bookingRequest)).thenReturn(miBookingResponse);
        BookingResponseDTO bookingResponse = this.bookingController.booking(bookingRequest);
        Mockito.verify(bookingService,atLeast(1)).booking(bookingRequest);
        assertEquals(miBookingResponse,bookingResponse);

    }
    @Test
    void bookingDebit() throws ApiException, ParseException {
        BookingRequestDTO bookingRequest = UtilTest.createRequest("DEBIT",1);
        BookingResponseDTO miBookingResponse = UtilTest.createResponse("DEBIT",1);
        Mockito.when(bookingService.booking(bookingRequest)).thenReturn(miBookingResponse);
        BookingResponseDTO bookingResponse = this.bookingController.booking(bookingRequest);
        Mockito.verify(bookingService,atLeast(1)).booking(bookingRequest);
        assertEquals(miBookingResponse,bookingResponse);
    }

    @Test
    void validateHotelException() throws ApiException, ParseException {
        BookingRequestDTO bookingRequest = UtilTest.createRequest("DEBIT",1);
        Mockito.when(bookingService.booking(bookingRequest)).thenThrow(new ApiException(HttpStatus.BAD_REQUEST, "Error: El destino elegido no existe."));
        String message = assertThrows(ApiException.class,()->this.bookingService.booking(bookingRequest)).getMessage();
        assertEquals("Error: El destino elegido no existe.", message);
    }


}