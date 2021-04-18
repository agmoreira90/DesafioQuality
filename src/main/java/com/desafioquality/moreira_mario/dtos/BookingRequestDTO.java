package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {
    private String userName;
    private BookingInDTO booking;

    public void setUserName(String userName) throws ApiException {
        this.userName = Util.emailFormat(userName);
    }
}
