package com.desafioquality.moreira_mario.service.reservation;

import com.desafioquality.moreira_mario.dtos.FlightReservationRequestDTO;
import com.desafioquality.moreira_mario.dtos.FlightReservationResponseDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;

public interface ReservationService {
    FlightReservationResponseDTO reservate(FlightReservationRequestDTO reservationRequest) throws ApiException;
}
