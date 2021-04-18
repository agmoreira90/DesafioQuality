package com.desafioquality.moreira_mario.controllers.booking;

import com.desafioquality.moreira_mario.dtos.BookingRequestDTO;
import com.desafioquality.moreira_mario.dtos.BookingResponseDTO;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.booking.BookingService;
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
}

