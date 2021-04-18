package com.desafioquality.moreira_mario.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private String flightNum;
    private String flightOrigin;
    private String flightDestination;
    private String seatType;
    private Double price;
    private Date flightDeparture;
    private Date flightArrival;
}
