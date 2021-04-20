package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.UtilValidate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class BookingInDTO {
    private Date dateFrom;
    private Date dateTo;
    private String destination;
    private String hotelCode;
    private Integer peopleAmount;
    private String roomType;
    private List<PersonDTO> people;
    private PaymentMethodDTO paymentMethod;
    @JsonIgnore
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void setDateFrom(String dateFrom) throws ApiException {
        this.dateFrom = UtilValidate.dateForamt(dateFrom);
    }

    public void setDateTo(String dateTo) throws ApiException {
        this.dateTo = UtilValidate.dateForamt(dateTo);
    }

    public void setPeopleAmount(String peopleAmount) throws ApiException {
        if (peopleAmount.matches("[+-]?\\d*(\\.\\d+)?")) {
            this.peopleAmount = Integer.parseInt(peopleAmount);
        } else {
            throw new ApiException(HttpStatus.CONFLICT, "Error: La cantidad de personas debe ser un valor numérico.");
        }
    }

    public void setRoomType(String roomType) throws ApiException {
        switch (roomType) {
            case "SINGLE":
                if (this.peopleAmount != 1)
                    this.executeRoomTypeException();
                break;
            case "DOBLE":
                if (this.peopleAmount != 2)
                    this.executeRoomTypeException();
                break;
            case "TRIPLE":
                if (this.peopleAmount != 3)
                    this.executeRoomTypeException();
                break;
            case "MULTIPLE":
                if (this.peopleAmount < 4 || this.peopleAmount > 10)
                    this.executeRoomTypeException();
                break;
            default:
                this.executeRoomTypeException();
                break;
        }
        this.roomType = roomType;
    }

    private void executeRoomTypeException() throws ApiException {
        throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.");
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
