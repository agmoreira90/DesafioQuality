package com.desafioquality.moreira_mario.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private String flightNumber;
    private String flightOrigin;
    private String flightDestination;
    private String seatType;
    private Double price;
    private Date flightDeparture;
    private Date flightArrival;

    public String getFlightDeparture() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.flightDeparture);
    }

    public String getFlightArrival() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.flightArrival);
    }
    @JsonIgnore
    public Date getFlightDepartureAsDate() {
        return this.flightDeparture;
    }
    @JsonIgnore
    public Date getFlightArrivalAsDate() {
        return this.flightArrival;
    }

}
