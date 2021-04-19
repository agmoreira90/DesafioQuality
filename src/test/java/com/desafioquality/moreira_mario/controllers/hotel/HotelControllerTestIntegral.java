package com.desafioquality.moreira_mario.controllers.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.hotel.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;

class HotelControllerTestIntegral {
    private HotelController hotelController;

    private HotelService hotelService;

    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        hotelService = mock(HotelService.class);
        this.hotelController = new HotelController(hotelService);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
    }

    @Test
    void selectHotelAll() throws ParseException, ApiException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        hotels.add(new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú", "Triple", 8200D, sdf.parse("10/02/2021"), sdf.parse("23/03/2021"), false));
        hotels.add(new HotelDTO("HB-0001", "Hotel Bristol", "Buenos Aires", "Single", 5435D, sdf.parse("10/02/2021"), sdf.parse("19/03/2021"), false));
        hotels.add(new HotelDTO("BH-0002", "Hotel Bristol 2", "Buenos Aires", "Doble", 7200D, sdf.parse("12/02/2021"), sdf.parse("17/04/2021"), false));
        Map<String, String> filters = new HashMap<>();
        Mockito.when(hotelService.getHotels(filters)).thenReturn(hotels);
        List<HotelDTO> newHotels = this.hotelController.getProducts(filters);
        Mockito.verify(hotelService,atLeast(1)).getHotels(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelCity() throws ParseException, ApiException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        hotels.add(new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú", "Triple", 8200D, sdf.parse("10/02/2021"), sdf.parse("23/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        Mockito.when(hotelService.getHotels(filters)).thenReturn(hotels);
        List<HotelDTO> newHotels = this.hotelController.getProducts(filters);
        Mockito.verify(hotelService,atLeast(1)).getHotels(filters);
        Assertions.assertEquals(hotels, newHotels);

    }

    @Test
    void selectHotelCityDate() throws ParseException, ApiException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        hotels.add(new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú", "Triple", 8200D, sdf.parse("10/02/2021"), sdf.parse("23/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        Mockito.when(hotelService.getHotels(filters)).thenReturn(hotels);
        List<HotelDTO> newHotels = this.hotelController.getProducts(filters);
        Mockito.verify(hotelService,atLeast(1)).getHotels(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelCityDateCode() throws ParseException, ApiException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        filters.put("hotelCode", "CH-0002");
        Mockito.when(hotelService.getHotels(filters)).thenReturn(hotels);
        List<HotelDTO> newHotels = this.hotelController.getProducts(filters);
        Mockito.verify(hotelService,atLeast(1)).getHotels(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelCityName() throws ParseException, ApiException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        filters.put("name", "Cataratas Hotel");
        Mockito.when(hotelService.getHotels(filters)).thenReturn(hotels);
        List<HotelDTO> newHotels = this.hotelController.getProducts(filters);
        Mockito.verify(hotelService,atLeast(1)).getHotels(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelRoomType() throws ParseException, ApiException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        filters.put("roomType", "Doble");
        Mockito.when(hotelService.getHotels(filters)).thenReturn(hotels);
        List<HotelDTO> newHotels = this.hotelController.getProducts(filters);
        Mockito.verify(hotelService,atLeast(1)).getHotels(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelPrice() throws ApiException, ParseException {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        filters.put("price", "6300");
        Mockito.when(hotelService.getHotels(filters)).thenReturn(hotels);
        List<HotelDTO> newHotels = this.hotelController.getProducts(filters);
        Mockito.verify(hotelService,atLeast(1)).getHotels(filters);
        Assertions.assertEquals(hotels, newHotels);
    }

    @Test
    void selectHotelCityException() throws ApiException {
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Ciudad que no Existe");
        Mockito.when(hotelService.getHotels(filters)).thenThrow(ApiException.class);
        assertThrows(ApiException.class, () -> hotelService.getHotels(filters));
    }
}