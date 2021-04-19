package com.desafioquality.moreira_mario.service.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.repositories.hotel.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class HotelServiceImpl implements HotelService{

    private HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Executes the search filtering
     *
     * @param params all the hotels filters
     * @return a filtered array list with hotels dto
     */
    @Override
    public List<HotelDTO> getHotels(Map<String, String> params) throws ApiException {
        List<HotelDTO> hotels = new ArrayList<HotelDTO>(this.hotelRepository.selectHotel(params).values());
        return hotels;
    }
    /**
     * Executes update of the dto
     *
     * @param hotel hotel dto
     */
    @Override
    public void updateHotel(HotelDTO hotel) throws ApiException {
        this.hotelRepository.updateHotel(hotel);
    }


}
