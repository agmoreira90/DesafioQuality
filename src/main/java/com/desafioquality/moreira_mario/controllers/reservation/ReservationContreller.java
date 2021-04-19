package com.desafioquality.moreira_mario.controllers.reservation;

import com.desafioquality.moreira_mario.dtos.*;
import com.desafioquality.moreira_mario.exceptions.ApiError;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.reservation.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v4")
public class ReservationContreller {
    private ReservationService reservationService;

    public ReservationContreller(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    /**
     * Post endpoint /booking
     *
     * @param reservationRequest BookingRequest dto
     * @return a ResponseBooking dto if the booking process ends correct
     */
    @PostMapping("/flight-reservation")
    public FlightReservationResponseDTO reservate(@RequestBody FlightReservationRequestDTO reservationRequest) throws ApiException {
        return reservationService.reservate(reservationRequest);
    }

    /**
     * Caths JSON Error
     *
     * @param e is a HttpMessageNotReadableException
     * @return a ResponseEntity<ApiError> with the error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> reservationJSONFormat(HttpMessageNotReadableException e) {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Error: Formato de JSON invalido.";
        String status = HttpStatus.BAD_REQUEST.name();
        ApiError apiError = new ApiError(status, message, statusCode);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
