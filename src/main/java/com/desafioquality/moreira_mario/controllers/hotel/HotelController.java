package com.desafioquality.moreira_mario.controllers.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiError;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.hotel.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HotelController {

    private HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    /**
     * GET endpoint /hotels
     *
     * @param params all the filters
     * @return a filtered list with hotels dto
     */
    @GetMapping("/hotels")
    public List<HotelDTO> getHotels(@RequestParam Map<String, String> params) throws ApiException {
        return hotelService.getHotels(params);
    }
}
