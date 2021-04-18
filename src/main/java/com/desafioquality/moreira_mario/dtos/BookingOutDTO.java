package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingOutDTO {
    private Date dateFrom;
    private Date dateTo;
    private String destination;
    private String hotelCode;
    private Integer peopleAmount;
    private String roomType;
    private List<PersonDTO> people;
    @JsonIgnore
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


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
}
