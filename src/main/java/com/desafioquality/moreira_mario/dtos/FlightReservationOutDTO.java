package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.UtilValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationOutDTO {

    private Date dateFrom;
    private Date dateTo;
    private String origin;
    private String destination;
    private String flightNumber;
    private Integer seats;
    private String seatType;
    private List<PersonDTO> people;


    public void setDateFrom(String dateFrom) throws ApiException {
        this.dateFrom = UtilValidate.dateForamt(dateFrom);
    }

    public void setDateTo(String dateTo) throws ApiException {
        this.dateTo = UtilValidate.dateForamt(dateTo);
    }


    public String getDateFrom() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dateFrom);
    }

    public String getDateTo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dateTo);
    }
}
