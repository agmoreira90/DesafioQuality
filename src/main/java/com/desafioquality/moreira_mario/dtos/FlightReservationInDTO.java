package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationInDTO {

    private Date dateFrom;
    private Date dateTo;
    private String origin;
    private String destination;
    private String flightNumber;
    private Integer seats;
    private String seatType;
    private List<PersonDTO> people;
    private PaymentMethodDTO paymentMethod;

    public void setDateFrom(String dateFrom) throws ApiException {
        this.dateFrom = Util.dateForamt(dateFrom);
    }

    public void setDateTo(String dateTo) throws ApiException {
        this.dateTo = Util.dateForamt(dateTo);
    }

    public String getDateFrom() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dateFrom);
    }

    public String getDateTo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dateTo);
    }


    @Override
    public String toString() {
        return "reservationFlight{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", seats=" + seats +
                ", seatType='" + seatType + '\'' +
                ", people=" + people +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
