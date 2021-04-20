package com.desafioquality.moreira_mario.controllers.flight;

import com.desafioquality.moreira_mario.dtos.FlightDTO;
import com.desafioquality.moreira_mario.exceptions.ApiError;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.flight.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    /**
     * GET endpoint /flights
     *
     * @param params all the filters
     * @return a filtered list with flight dto
     */
    @GetMapping("/flights")
    public List<FlightDTO> getFlights(@RequestParam Map<String, String> params) throws ApiException {
        return flightService.getFlights(params);
    }
}
