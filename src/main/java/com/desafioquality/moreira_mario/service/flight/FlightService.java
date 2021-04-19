package com.desafioquality.moreira_mario.service.flight;

import com.desafioquality.moreira_mario.dtos.FlightDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;

import java.util.List;
import java.util.Map;

public interface FlightService {


    List<FlightDTO> getFlights(Map<String,String> params) throws ApiException;
}
