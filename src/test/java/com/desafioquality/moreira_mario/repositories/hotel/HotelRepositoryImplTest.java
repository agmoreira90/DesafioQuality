package com.desafioquality.moreira_mario.repositories.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HotelRepositoryImplTest {

    private HotelRepository hotelRepository;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        this.hotelRepository = new HotelRepositoryImpl("classpath:AgencyTest.properties", "hotelSheet");
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
    }

    @Test
    void selectHotelAll() throws ParseException, ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        hotels.put("2", new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú", "Triple", 8200D, sdf.parse("10/02/2021"), sdf.parse("23/03/2021"), false));
        hotels.put("3", new HotelDTO("HB-0001", "Hotel Bristol", "Buenos Aires", "Single", 5435D, sdf.parse("10/02/2021"), sdf.parse("19/03/2021"), false));
        hotels.put("4", new HotelDTO("BH-0002", "Hotel Bristol 2", "Buenos Aires", "Doble", 7200D, sdf.parse("12/02/2021"), sdf.parse("17/04/2021"), false));
        Map<String, String> filters = new HashMap<>();
        Map<String, HotelDTO> newHotels = hotelRepository.selectHotel(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelCity() throws ParseException, ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        hotels.put("2", new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú", "Triple", 8200D, sdf.parse("10/02/2021"), sdf.parse("23/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        Map<String, HotelDTO> newHotels = hotelRepository.selectHotel(filters);
        Assertions.assertEquals(hotels, newHotels);

    }

    @Test
    void selectHotelCityDate() throws ParseException, ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        hotels.put("2", new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú", "Triple", 8200D, sdf.parse("10/02/2021"), sdf.parse("23/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        Map<String, HotelDTO> newHotels = hotelRepository.selectHotel(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelCityDateCode() throws ParseException, ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        filters.put("hotelCode", "CH-0002");
        Map<String, HotelDTO> newHotels = hotelRepository.selectHotel(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelCityName() throws ParseException, ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        filters.put("name", "Cataratas Hotel");
        Map<String, HotelDTO> newHotels = hotelRepository.selectHotel(filters);

        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelRoomType() throws ParseException, ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "20/03/2021");
        filters.put("roomType", "Doble");
        Map<String, HotelDTO> newHotels = hotelRepository.selectHotel(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelPrice() throws ApiException, ParseException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        filters.put("price", "6300");
        Map<String, HotelDTO> newHotels = hotelRepository.selectHotel(filters);
        Assertions.assertEquals(hotels, newHotels);
    }



    @Test
    void selectHotelCityException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Ciudad que no Existe");
        assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters));
    }

    @Test
    void selectHotelDateFormatException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("availabilityFrom", "10/0f/2021");
        filters.put("availabilityTo", "1e/03/2021");
        String message = assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters)).getMessage();
        assertEquals("Error: Formato de fecha filtros debe ser dd/mm/aaaa.", message);
    }

    @Test
    void selectHotelDateWithoutTOException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("availabilityFrom", "10/03/2021");
        String message = assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters)).getMessage();
        assertEquals("Error: Debe cargar fecha de entrada y de salida.", message);
    }

    @Test
    void selectHotelDateWithoutFromException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("availabilityTo", "10/03/2021");
        String message = assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters)).getMessage();
        assertEquals("Error: Debe cargar fecha de entrada y de salida.", message);
    }

    @Test
    void selectHotelDateOrderException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("availabilityTo", "10/03/2021");
        filters.put("availabilityFrom", "11/03/2021");
        String message = assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters)).getMessage();
        assertEquals("Error: La fecha de salida debe ser mayor a la de entrada.", message);
    }

    @Test
    void selectHotelAvailabilityToException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("availabilityTo", "10/04/2023");
        filters.put("availabilityFrom", "11/03/2023");
        String message = assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters)).getMessage();
        assertEquals("Informacion: No Existen Hoteles disponibles para ese rango de fechas.", message);
    }

    @Test
    void selectHotelIsReservadoException() {
        this.hotelRepository = new HotelRepositoryImpl("classpath:AgencyTest.properties", "IsReservadoException");
        Map<String, String> filters = new HashMap<>();
        filters.put("availabilityTo", "15/03/2022");
        filters.put("availabilityFrom", "10/03/2022");
        String message = assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters)).getMessage();
        assertEquals("Informcion: No hay reservas disponibles.", message);
    }

    @Test
    void selectHotelXLSXDateFormatException() {
        this.hotelRepository = new HotelRepositoryImpl("classpath:AgencyTest.properties", "DateFormatException");
        Map<String, String> filters = new HashMap<>();
        filters.put("availabilityTo", "11/03/2021");
        filters.put("availabilityFrom", "10/03/2021");
        String message = assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters)).getMessage();
        assertEquals("Error: Formato de Fecha en Hotel: Cataratas Hotel no valido.", message);
    }

    @Test
    void updateHotel() throws ApiException, ParseException {
        this.hotelRepository = new HotelRepositoryImpl("classpath:AgencyTest.properties", "UpdateHotel");

        HotelDTO hotel = new HotelDTO("CH-0003", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2022"), sdf.parse("20/03/2022"), false);
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2022");
        filters.put("availabilityTo", "20/03/2022");
        filters.put("hotelCode", "CH-0003");
        hotelRepository.updateHotel(hotel);
        String message = assertThrows(ApiException.class, () -> hotelRepository.selectHotel(filters)).getMessage();
        assertEquals("Informcion: No hay reservas disponibles.", message);
        hotel = new HotelDTO("CH-0003", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2022"), sdf.parse("20/03/2022"), true);
        hotelRepository.updateHotel(hotel);
    }
}