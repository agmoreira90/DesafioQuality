package com.desafioquality.moreira_mario.controllers.flight;

import com.desafioquality.moreira_mario.dtos.FlightDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.repositories.flight.FlightRepository;
import com.desafioquality.moreira_mario.service.flight.FlightService;
import com.desafioquality.moreira_mario.service.flight.FlightServiceImpl;
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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;

class FlightControllerTest {

    private FlightController flightController;

    private FlightService flightService;

    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        this.flightService = mock(FlightService.class);
        this.flightController = new FlightController(flightService);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
    }

    @Test
    void selectFlightAll() throws ParseException, ApiException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add(new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", 6500D, sdf.parse("10/02/2021"), sdf.parse("15/02/2021")));
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
        flights.add(new FlightDTO("BATU-5536", "Buenos Aires", "Tucumán", "Economy", 7320D, sdf.parse("10/02/2021"), sdf.parse("17/02/2021")));
        Map<String, String> filters = new HashMap<>();
        Mockito.when(flightService.getFlights(filters)).thenReturn(flights);
        List<FlightDTO> newflights = this.flightController.getFlights(filters);
        Mockito.verify(flightService,atLeast(1)).getFlights(filters);
        Assertions.assertEquals(flights, newflights);
    }

    @Test
    void selectFlightOrigin() throws ParseException, ApiException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
       Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        Mockito.when(flightService.getFlights(filters)).thenReturn(flights);
        List<FlightDTO> newflights = this.flightController.getFlights(filters);
        Mockito.verify(flightService,atLeast(1)).getFlights(filters);
        Assertions.assertEquals(flights, newflights);

    }

    @Test
    void selectFlightDestination() throws ParseException, ApiException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        Mockito.when(flightService.getFlights(filters)).thenReturn(flights);
        List<FlightDTO> newflights = this.flightController.getFlights(filters);
        Mockito.verify(flightService,atLeast(1)).getFlights(filters);
        Assertions.assertEquals(flights, newflights);
    }

    @Test
    void selectFlightOriginDestinationDate() throws ParseException, ApiException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        filters.put("flightDeparture", "10/02/2021");
        filters.put("flightArrival", "21/02/2021");
         Mockito.when(flightService.getFlights(filters)).thenReturn(flights);
        List<FlightDTO> newflights = this.flightController.getFlights(filters);
        Mockito.verify(flightService,atLeast(1)).getFlights(filters);
        Assertions.assertEquals(flights, newflights);
    }

    @Test
    void selectFlightOriginDestinationDateNumber() throws ParseException, ApiException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735D, sdf.parse("10/02/2021"), sdf.parse("21/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        filters.put("flightDeparture", "10/02/2021");
        filters.put("flightArrival", "20/02/2021");
        filters.put("flightNum", "PIBA-1420");
         Mockito.when(flightService.getFlights(filters)).thenReturn(flights);
        List<FlightDTO> newflights = this.flightController.getFlights(filters);
        Mockito.verify(flightService,atLeast(1)).getFlights(filters);
        Assertions.assertEquals(flights, newflights);
    }

    @Test
    void selectFlightOriginDestinationDateSeatType() throws ParseException, ApiException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        filters.put("flightDeparture", "10/02/2021");
        filters.put("flightArrival", "20/02/2021");
        filters.put("flightNum", "PIBA-1420");
        filters.put("seatType", "Business");
         Mockito.when(flightService.getFlights(filters)).thenReturn(flights);
        List<FlightDTO> newflights = this.flightController.getFlights(filters);
        Mockito.verify(flightService,atLeast(1)).getFlights(filters);
        Assertions.assertEquals(flights, newflights);
    }

    @Test
    void selectFlightOriginDestinationDateSeatTypePrice() throws ApiException, ParseException {
        List<FlightDTO> flights = new ArrayList<>();
        flights.add(new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200D, sdf.parse("10/02/2021"), sdf.parse("20/02/2021")));
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        filters.put("flightDestination", "Bogotá");
        filters.put("flightDeparture", "10/02/2021");
        filters.put("flightArrival", "20/02/2021");
        filters.put("flightNum", "PIBA-1420");
        filters.put("seatType", "Business");
        filters.put("price", "43200");
         Mockito.when(flightService.getFlights(filters)).thenReturn(flights);
        List<FlightDTO> newflights = this.flightController.getFlights(filters);
        Mockito.verify(flightService,atLeast(1)).getFlights(filters);
        Assertions.assertEquals(flights, newflights);
    }



    @Test
    void selectFlightException() throws ApiException {
        Map<String, String> filters = new HashMap<>();
        filters.put("flightOrigin", "Puerto Iguazú");
        Mockito.when(flightService.getFlights(filters)).thenThrow(ApiException.class);
        assertThrows(ApiException.class, () -> flightService.getFlights(filters));
    }
}