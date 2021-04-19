package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.UtilValidate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingRequestDTO {
    private String userName;
    private BookingInDTO booking;

    public void setUserName(String userName) throws ApiException {
        this.userName = UtilValidate.emailFormat(userName);
    }
}
