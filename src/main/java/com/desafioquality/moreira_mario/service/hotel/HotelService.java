package com.desafioquality.moreira_mario.service.hotel;

import com.desafioquality.moreira_mario.dtos.BookingRequestDTO;
import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface HotelService {

    List<HotelDTO> getHotels(Map<String,String> params) throws ApiException;
    void updateHotel(HotelDTO hotel) throws  ApiException;
}
