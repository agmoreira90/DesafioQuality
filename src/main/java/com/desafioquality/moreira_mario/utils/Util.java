package com.desafioquality.moreira_mario.utils;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    /**
     * Executes date conversion
     *
     * @param date a string date format
     * @return a date
     */
    public static Date dateForamt(String date) throws ApiException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date formatedDate = sdf.parse(date);
            return formatedDate;
        } catch (ParseException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: Formato de fecha filtros debe ser dd/mm/aaaa.");
        }
    }

    /**
     * Executes email validation
     *
     * @param email given by the user
     * @return the email if it matches the regex
     */
    public static String emailFormat(String email) throws ApiException {
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (!email.matches(regex)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: Por favor ingrese un e-mail válido.");
        } else {
            return email;
        }
    }
}
