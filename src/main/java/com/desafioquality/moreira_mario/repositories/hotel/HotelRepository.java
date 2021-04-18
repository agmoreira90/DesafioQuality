package com.desafioquality.moreira_mario.repositories.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;

import java.text.ParseException;
import java.util.Map;

public interface HotelRepository {

    Map<String, HotelDTO> selectHotel(Map<String, String> params) throws ApiException;

    void updateHotel(HotelDTO hotel) throws ApiException;

}
