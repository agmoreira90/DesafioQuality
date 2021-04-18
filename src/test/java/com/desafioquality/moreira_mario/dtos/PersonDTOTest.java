package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

    @Test
    void setBirthDateOK() throws ApiException {
        PersonDTO person = new PersonDTO();
        person.setBirthDate("05/10/1990");
        assertEquals("05/10/1990",person.getBirthDate());
    }
    @Test
    void setBirthDateException() throws ApiException {
        PersonDTO person = new PersonDTO();
        String message = assertThrows(ApiException.class, ()->person.setBirthDate("05/25/1990")).getMessage();
        assertEquals("Error: Formato de fecha filtros debe ser dd/mm/aaaa.",message);
    }

    @Test
    void setMailOk() throws ApiException {
        PersonDTO person = new PersonDTO();
        person.setMail("agustin.moreira@mercadolibre.com");
        assertEquals("agustin.moreira@mercadolibre.com",person.getMail());
    }

    @Test
    void getBirthDateException() {
        PersonDTO person = new PersonDTO();
        String message = assertThrows(ApiException.class, ()->person.setMail("agustin.moreira@.com")).getMessage();
        assertEquals("Error: Por favor ingrese un e-mail válido.",message);
    }
}