package com.desafioquality.moreira_mario.repositories.flight;

import com.desafioquality.moreira_mario.dtos.FlightDTO;
import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;

import java.util.Map;

public interface FlightRepository {

    Map<String, FlightDTO> selectFlight(Map<String, String> params) throws ApiException;

    void updateFlight(FlightDTO flight) throws ApiException;

}
