package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.UtilValidate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private String hotelCode;
    private String name;
    private String city;
    private String roomType;
    private Double price;
    private Date availabilityFrom;
    private Date availabilityTo;
    private boolean isReserved;

    public String getAvailabilityFrom() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.availabilityFrom);
    }

    public String getAvailabilityTo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.availabilityTo);
    }
    @JsonIgnore
    public Date getAvailabilityFromAsDate() {
        return this.availabilityFrom;
    }
    @JsonIgnore
    public Date getAvailabilityToAsDate() {
        return this.availabilityTo;
    }

    public void setAvailabilityFrom(String availabilityFrom) throws ApiException {
        this.availabilityFrom = UtilValidate.dateForamt(availabilityFrom);
    }

    public void setAvailabilityTo(String availabilityTo) throws ApiException {
        this.availabilityTo = UtilValidate.dateForamt(availabilityTo);
    }
}
