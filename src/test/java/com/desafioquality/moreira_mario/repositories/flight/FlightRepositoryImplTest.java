package com.desafioquality.moreira_mario.repositories.flight;

import com.desafioquality.moreira_mario.dtos.FlightDTO;
import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.repositories.hotel.HotelRepository;
import com.desafioquality.moreira_mario.repositories.hotel.HotelRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FlightRepositoryImplTest {
    private FlightRepository flightRepository;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        this.flightRepository = new FlightRepositoryImpl("classpath:AgencyTest.properties", "flightSheet");
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
    }

    @Test
    void selectFlightAll() throws ParseException, ApiException {
        Map<String, FlightDTO> flights = new HashMap<>();
        flights.put("1", new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", 6500D, sdf.parse("10/02/2021"), sdf.parse("15/02/2021")));
        flights.put("2", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.put("3", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
        flights.put("4", new FlightDTO("BATU-5536", "Buenos Aires", "Tucumán", "Economy", 7320D, sdf.parse("10/02/2021"), sdf.parse("17/02/2021")));
        Map<String, String> filters = new HashMap<>();
        Map<String, FlightDTO> newFlights = flightRepository.selectFlight(filters);
        Assertions.assertEquals(flights, newFlights);
    }

    @Test
    void selectFlightlOrigin() throws ParseException, ApiException {
        Map<String, FlightDTO> flights = new HashMap<>();
        flights.put("2", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.put("3", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        Map<String, FlightDTO> newFlights = flightRepository.selectFlight(filters);
        Assertions.assertEquals(flights, newFlights);

    }

    @Test
    void selectFlightOriginDestination() throws ParseException, ApiException {
        Map<String, FlightDTO> flights = new HashMap<>();
        flights.put("2", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.put("3", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        Map<String, FlightDTO> newFlights = flightRepository.selectFlight(filters);
        Assertions.assertEquals(flights, newFlights);

    }

    @Test
    void selectFlightOriginDestinationDate() throws ParseException, ApiException {
        Map<String, FlightDTO> flights = new HashMap<>();
        flights.put("2", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.put("3", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        filters.put("flightDeparture", "10/02/2021");
        filters.put("flightArrival", "21/02/2021");
        Map<String, FlightDTO> newFlights = flightRepository.selectFlight(filters);
        Assertions.assertEquals(flights, newFlights);
    }

    @Test
    void selectFlightOriginDestinationDateNumber() throws ParseException, ApiException {
        Map<String, FlightDTO> flights = new HashMap<>();
        flights.put("2", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        filters.put("flightDeparture", "10/02/2021");
        filters.put("flightArrival", "20/02/2021");
        filters.put("flightNum", "PIBA-1420");
        Map<String, FlightDTO> newFlights = flightRepository.selectFlight(filters);
        Assertions.assertEquals(flights, newFlights);
    }

    @Test
    void selectFlightOriginDestinationDateSeatType() throws ParseException, ApiException {
        Map<String, FlightDTO> flights = new HashMap<>();
        flights.put("2", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        filters.put("flightDeparture", "10/02/2021");
        filters.put("flightArrival", "20/02/2021");
        filters.put("flightNum", "PIBA-1420");
        filters.put("seatType", "Business");
        Map<String, FlightDTO> newFlights = flightRepository.selectFlight(filters);
        Assertions.assertEquals(flights, newFlights);
    }

    @Test
    void selectFlightOriginDestinationDateSeatTypePrice() throws ParseException, ApiException {
        Map<String, FlightDTO> flights = new HashMap<>();
        flights.put("2", new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        filters.put("flightDeparture", "10/02/2021");
        filters.put("flightArrival", "20/02/2021");
        filters.put("flightNum", "PIBA-1420");
        filters.put("seatType", "Business");
        filters.put("price", "43200");
        Map<String, FlightDTO> newFlights = flightRepository.selectFlight(filters);
        Assertions.assertEquals(flights, newFlights);
    }


    @Test
    void selectHotelDateFormatException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("flightDeparture", "10/0f/2021");
        filters.put("flightArrival", "1e/03/2021");
        String message = assertThrows(ApiException.class, () -> flightRepository.selectFlight(filters)).getMessage();
        assertEquals("Error: Formato de fecha filtros debe ser dd/mm/aaaa.", message);
    }

    @Test
    void selectHotelDateWithoutTOException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("flightDeparture", "10/03/2021");
        String message = assertThrows(ApiException.class, () -> flightRepository.selectFlight(filters)).getMessage();
        assertEquals("Error: Debe cargar fecha de entrada y de salida.", message);
    }

    @Test
    void selectHotelDateWithoutFromException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("flightArrival", "10/03/2021");
        String message = assertThrows(ApiException.class, () -> flightRepository.selectFlight(filters)).getMessage();
        assertEquals("Error: Debe cargar fecha de entrada y de salida.", message);
    }

    @Test
    void selectHotelDateOrderException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("flightArrival", "10/03/2021");
        filters.put("flightDeparture", "11/03/2021");
        String message = assertThrows(ApiException.class, () -> flightRepository.selectFlight(filters)).getMessage();
        assertEquals("Error: La fecha de vuelta debe ser mayor a la de ida.", message);
    }

    @Test
    void selectHotelAvailabilityToException() {
        Map<String, String> filters = new HashMap<>();
        filters.put("flightArrival", "10/04/2023");
        filters.put("flightDeparture", "11/03/2023");
        String message = assertThrows(ApiException.class, () -> flightRepository.selectFlight(filters)).getMessage();
        assertEquals("Informacion: No Existen vuelos disponibles para ese rango de fechas.", message);
    }


    @Test
    void selectHotelXLSXDateFormatException() {
        this.flightRepository = new FlightRepositoryImpl("classpath:AgencyTest.properties", "flightDateFormatException");
        Map<String, String> filters = new HashMap<>();
        filters.put("availabilityTo", "11/03/2021");
        filters.put("availabilityFrom", "10/03/2021");
        String message = assertThrows(ApiException.class, () -> flightRepository.selectFlight(filters)).getMessage();
        assertEquals("Error: Formato de Fecha en Vuelo: BAPI-1235 no valido.", message);
    }

    @Test
    void selectFlightDestinationException() throws ParseException, ApiException {
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Aeropuerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        String message = assertThrows(ApiException.class, () -> flightRepository.selectFlight(filters)).getMessage();
        assertEquals("Error: El origen elegido no existe.", message);

    }
    @Test
    void selectFlightOriginException() throws ParseException, ApiException {
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogota");
        String message = assertThrows(ApiException.class, () -> flightRepository.selectFlight(filters)).getMessage();
        assertEquals("Error: El destino elegido no existe.", message);

    }

}