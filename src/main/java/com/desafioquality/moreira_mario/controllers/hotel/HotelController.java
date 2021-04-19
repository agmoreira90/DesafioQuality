package com.desafioquality.moreira_mario.controllers.hotel;

import com.desafioquality.moreira_mario.dtos.HotelDTO;
import com.desafioquality.moreira_mario.exceptions.ApiError;
import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.service.hotel.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

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
    /**
     * Caths JSON Error
     *
     * @param e is a HttpMessageNotReadableException
     * @return a ResponseEntity<ApiError> with the error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> hotelSONFormat(HttpMessageNotReadableException e) {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Error: Formato de JSON invalido.";
        String status = HttpStatus.BAD_REQUEST.name();
        ApiError apiError = new ApiError(status, message, statusCode);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
