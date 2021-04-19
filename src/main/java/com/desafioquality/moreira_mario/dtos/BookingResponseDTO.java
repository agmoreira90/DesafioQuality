package com.desafioquality.moreira_mario.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingResponseDTO {
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;
    private BookingOutDTO booking;
    private StatusCodeDTO statusCode;
}
