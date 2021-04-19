package com.desafioquality.moreira_mario.service.flight;

import com.desafioquality.moreira_mario.dtos.FlightDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.repositories.flight.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class FlightServiceImpl implements FlightService{

    private FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    /**
     * Executes the search filtering
     *
     * @param params all the flight filters
     * @return a filtered array list with flight dto
     */
    @Override
    public List<FlightDTO> getFlights(Map<String, String> params) throws ApiException {
        List<FlightDTO> flights = new ArrayList<FlightDTO>(this.flightRepository.selectFlight(params).values());
        return flights;
    }
}
