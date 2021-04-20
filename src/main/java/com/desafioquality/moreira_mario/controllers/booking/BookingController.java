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
@RequestMapping("/api/v1")
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
     * Payload
     * {
     *     "userName" : "seba_gonzalez@unmail.com",
     *     "booking" : {
     *         "dateFrom" : "20/02/2021",
     *         "dateTo" : "28/02/2021",
     *         "destination" : "Buenos Aires",
     *         "hotelCode" : "BH-0002",
     *         "peopleAmount" : 2,
     *         "roomType" : "DOBLE",
     *         "people" : [
     *             {
     *                 "dni" : "12345678",
     *                 "name" : "Pepito",
     *                 "lastName" : "Gomez",
     *                 "birthDate" : "10/11/1982",
     *                 "mail" : "pepitogomez@gmail.com"
     *             },
     *              {
     *                 "dni" : "13345678",
     *                 "name" : "Fulanito",
     *                 "lastName" : "Gomez",
     *                 "birthDate" : "10/11/1983",
     *                 "mail" : "fulanitogomez@gmail.com"
     *             }
     *         ],
     *         "paymentMethod" : {
     *             "type" : "CREDIT",
     *             "number" : "1234-1234-1234-1234",
     *             "dues" : 3
     *         }
     *     }
     * }
     */



    /**
     * Catch JSON Error
     *
     * @param e is a HttpMessageNotReadableException
     * @return a ResponseEntity<ApiError> with the error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> bookingJSONFormat(HttpMessageNotReadableException e) {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Error: Formato de JSON invalido.";
        String status = HttpStatus.BAD_REQUEST.name();
        ApiError apiError = new ApiError(status, message, statusCode);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}

