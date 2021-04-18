package com.desafioquality.moreira_mario.controllers.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.hotel.HotelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<HotelDTO> getProducts(@RequestParam Map<String, String> params) throws ApiException {
        return hotelService.getHotels(params);
    }
}
