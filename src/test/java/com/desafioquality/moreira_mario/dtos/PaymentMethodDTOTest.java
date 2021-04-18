package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodDTOTest {

    @Test
    void setTypeCredit() throws ApiException {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("CREDIT");
        paymentMethod.setNumber("1234-1234-1234-1234");
        paymentMethod.setDues("2");

        assertEquals("CREDIT",paymentMethod.getType());
        assertEquals("1234-1234-1234-1234",paymentMethod.getNumber());
        assertEquals(2,paymentMethod.getDues());
    }
    @Test
    void setTypeDebit() throws ApiException {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("DEBIT");
        paymentMethod.setNumber("1234-1234-1234-1234");
        paymentMethod.setDues("1");

        assertEquals("DEBIT",paymentMethod.getType());
        assertEquals("1234-1234-1234-1234",paymentMethod.getNumber());
        assertEquals(1,paymentMethod.getDues());
    }
    @Test
    void setTypeException() throws ApiException {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        String message = assertThrows(ApiException.class, ()-> paymentMethod.setType("PEPE")).getMessage();
        assertEquals("Error: El tipo de Tarjeta seleccionado no es correcto.",message);
    }

    @Test
    void setDuesCreditException() throws ApiException {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("CREDIT");
        String message = assertThrows(ApiException.class, ()-> paymentMethod.setDues("0")).getMessage();
        assertEquals("Error: Se ha ingresado una cantidad de cuotas invalida.",message);
        message = assertThrows(ApiException.class, ()-> paymentMethod.setDues("7")).getMessage();
        assertEquals("Error: Se ha ingresado una cantidad de cuotas invalida.",message);
    }
    @Test
    void setDuesDebitException() throws ApiException {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("DEBIT");
        String message = assertThrows(ApiException.class, ()-> paymentMethod.setDues("3")).getMessage();
        assertEquals("Error: Se ha ingresado una cantidad de cuotas diferente a 1.",message);
    }
    @Test
    void setDuesException() {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        String message = assertThrows(ApiException.class, ()-> paymentMethod.setDues("E")).getMessage();
        assertEquals("Error: La cantidad de cuotas debe ser un valor numérico.",message);
    }
}