package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingInDTOTest {

    @Test
    void setPeopleAmountException() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        String message =  assertThrows(ApiException.class,()-> booking.setPeopleAmount("s")).getMessage();
        assertEquals("Error: La cantidad de personas debe ser un valor numérico.",message);
    }

    @Test
    void setRoomType() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        booking.setPeopleAmount("2");
        assertDoesNotThrow(()-> booking.setRoomType("DOBLE"));
        assertEquals(2,booking.getPeopleAmount());
    }

    @Test
    void setRoomTypeSingleException() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        booking.setPeopleAmount("2");
        String message =  assertThrows(ApiException.class,()-> booking.setRoomType("SINGLE")).getMessage();
        assertEquals("Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.",message);
    }

    @Test
    void setRoomTypeDefaultException() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        booking.setPeopleAmount("2");
        String message =  assertThrows(ApiException.class,()-> booking.setRoomType("PRUEBA")).getMessage();
        assertEquals("Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.",message);
    }

    @Test
    void setRoomTypeDobleException() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        booking.setPeopleAmount("1");
        String message =  assertThrows(ApiException.class,()-> booking.setRoomType("DOBLE")).getMessage();
        assertEquals("Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.",message);
        booking.setPeopleAmount("3");
        message =  assertThrows(ApiException.class,()-> booking.setRoomType("DOBLE")).getMessage();
        assertEquals("Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.",message);
    }
    @Test
    void setRoomTypeTripleException() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        booking.setPeopleAmount("2");
        String message =  assertThrows(ApiException.class,()-> booking.setRoomType("TRIPLE")).getMessage();
        assertEquals("Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.",message);
        booking.setPeopleAmount("4");
        message =  assertThrows(ApiException.class,()-> booking.setRoomType("TRIPLE")).getMessage();
        assertEquals("Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.",message);
    }
    @Test
    void setRoomTypeMultipleException() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        booking.setPeopleAmount("3");
        String message =  assertThrows(ApiException.class,()-> booking.setRoomType("MULTIPLE")).getMessage();
        assertEquals("Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.",message);
        booking.setPeopleAmount("11");
        message =  assertThrows(ApiException.class,()-> booking.setRoomType("MULTIPLE")).getMessage();
        assertEquals("Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.",message);
    }

    @Test
    void setDateFrom() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        booking.setDateFrom("05/10/1990");
        assertEquals("05/10/1990",booking.getDateFrom());
    }

    @Test
    void setDateFromException() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        String message =  assertThrows(ApiException.class,()-> booking.setDateFrom("05/L0/1990")).getMessage();
        assertEquals("Error: Formato de fecha filtros debe ser dd/mm/aaaa.",message);
    }

    @Test
    void setDateTo() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        booking.setDateTo("05/10/1990");
        assertEquals("05/10/1990",booking.getDateTo());
    }

    @Test
    void setDateToException() throws ApiException {
        BookingInDTO booking = new BookingInDTO();
        String message =  assertThrows(ApiException.class,()-> booking.setDateTo("05/L0/1990")).getMessage();
        assertEquals("Error: Formato de fecha filtros debe ser dd/mm/aaaa.",message);
    }
}