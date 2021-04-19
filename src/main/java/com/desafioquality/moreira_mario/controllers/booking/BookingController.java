package com.desafioquality.moreira_mario.controllers.booking;

import com.desafioquality.moreira_mario.dtos.BookingRequestDTO;
import com.desafioquality.moreira_mario.dtos.BookingResponseDTO;
import com.desafioquality.moreira_mario.exceptions.ApiError;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.booking.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2")
public class BookingController {


    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    /**
     * Post endpoint /booking
     *
     * @param bookingRequest BookingRequest dto
     * @return a ResponseBooking dto if the booking process ends correct
     */
    @PostMapping("/booking")
    public BookingResponseDTO booking(@RequestBody BookingRequestDTO bookingRequest) throws ApiException {
        return bookingService.booking(bookingRequest);
    }
    /**
     * Caths JSON Error
     *
     * @param e is a HttpMessageNotReadableException
     * @return a ResponseEntity<ApiError> with the error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> productJSONFormat(HttpMessageNotReadableException e) {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Error: Formato de JSON invalido.";
        String status = HttpStatus.BAD_REQUEST.name();
        ApiError apiError = new ApiError(status, message, statusCode);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}

