package com.desafioquality.moreira_mario.service.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.repositories.hotel.HotelRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {
    private HotelService hotelService;

    private HotelRepository hotelRepository;

    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        hotelRepository = mock(HotelRepository.class);
        this.hotelService = new HotelServiceImpl(hotelRepository);
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
        Mockito.when(hotelRepository.selectHotel(filters)).thenReturn(hotels);
        List<HotelDTO> hotelsList = new ArrayList<HotelDTO>(hotels.values());
        List<HotelDTO> newHotels = this.hotelService.getHotels(filters);
        Mockito.verify(hotelRepository,atLeast(1)).selectHotel(filters);
        Assertions.assertEquals(hotelsList, newHotels);
    }

    @Test
    void selectHotelCity() throws ParseException, ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        hotels.put("2", new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú", "Triple", 8200D, sdf.parse("10/02/2021"), sdf.parse("23/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        Mockito.when(hotelRepository.selectHotel(filters)).thenReturn(hotels);
        List<HotelDTO> hotelsList = new ArrayList<HotelDTO>(hotels.values());
        List<HotelDTO> newHotels = this.hotelService.getHotels(filters);
        Mockito.verify(hotelRepository,atLeast(1)).selectHotel(filters);
        Assertions.assertEquals(hotelsList, newHotels);

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
        Mockito.when(hotelRepository.selectHotel(filters)).thenReturn(hotels);
        List<HotelDTO> hotelsList = new ArrayList<HotelDTO>(hotels.values());
        List<HotelDTO> newHotels = this.hotelService.getHotels(filters);
        Mockito.verify(hotelRepository,atLeast(1)).selectHotel(filters);
        Assertions.assertEquals(hotelsList, newHotels);
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
        Mockito.when(hotelRepository.selectHotel(filters)).thenReturn(hotels);
        List<HotelDTO> hotelsList = new ArrayList<HotelDTO>(hotels.values());
        List<HotelDTO> newHotels = this.hotelService.getHotels(filters);
        Mockito.verify(hotelRepository,atLeast(1)).selectHotel(filters);
        Assertions.assertEquals(hotelsList, newHotels);
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
        Mockito.when(hotelRepository.selectHotel(filters)).thenReturn(hotels);
        List<HotelDTO> hotelsList = new ArrayList<HotelDTO>(hotels.values());
        List<HotelDTO> newHotels = this.hotelService.getHotels(filters);
        Mockito.verify(hotelRepository,atLeast(1)).selectHotel(filters);
        Assertions.assertEquals(hotelsList, newHotels);
    }

    @Test
    void selectHotelRoomType() throws ParseException, ApiException {
        Map<String, HotelDTO> hotels = new HashMap<>();
        hotels.put("1", new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2021"), sdf.parse("20/03/2021"), false));
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Puerto Iguazú");
        filters.put("availabilityFrom", "10/02/2021");
        filters.put("availabilityTo", "19/03/2021");
        filters.put("roomType", "Doble");
        Mockito.when(hotelRepository.selectHotel(filters)).thenReturn(hotels);
        List<HotelDTO> hotelsList = new ArrayList<HotelDTO>(hotels.values());
        List<HotelDTO> newHotels = this.hotelService.getHotels(filters);
        Mockito.verify(hotelRepository,atLeast(1)).selectHotel(filters);
        Assertions.assertEquals(hotelsList, newHotels);
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
        Mockito.when(hotelRepository.selectHotel(filters)).thenReturn(hotels);
        List<HotelDTO> hotelsList = new ArrayList<HotelDTO>(hotels.values());
        List<HotelDTO> newHotels = this.hotelService.getHotels(filters);
        Mockito.verify(hotelRepository,atLeast(1)).selectHotel(filters);
        Assertions.assertEquals(hotelsList, newHotels);
    }



    @Test
    void selectHotelException() throws ApiException {
        Map<String, String> filters = new HashMap<>();
        filters.put("city", "Ciudad que no Existe");
        Mockito.when(hotelRepository.selectHotel(filters)).thenThrow(ApiException.class);
        assertThrows(ApiException.class, () -> hotelService.getHotels(filters));
    }

    @Test
    void updateHotel() throws ApiException, ParseException {
        HotelDTO hotel = new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300D, sdf.parse("10/02/2022"), sdf.parse("23/03/2022"), true);
        assertDoesNotThrow(() -> hotelService.updateHotel(hotel));
    }
}