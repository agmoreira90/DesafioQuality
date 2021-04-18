package com.desafioquality.moreira_mario.service.booking;

import com.desafioquality.moreira_mario.dtos.BookingRequestDTO;
import com.desafioquality.moreira_mario.dtos.BookingResponseDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;

public interface BookingService {
    BookingResponseDTO booking(BookingRequestDTO bookingRequestDTO) throws ApiException;
}
