package com.desafioquality.moreira_mario.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;
    private BookingOutDTO booking;
    private StatusCodeDTO statusCode;
}
