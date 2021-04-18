package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDTO {
    private String type;
    private String number;
    private Integer dues;

    public void setType(String type) throws ApiException {
        switch (type) {
            case "CREDIT":
                this.type = type;
                break;
            case "DEBIT":
                this.type = type;
                break;
            default:
                throw new ApiException(HttpStatus.BAD_REQUEST, "Error: El tipo de Tarjeta seleccionado no es correcto.");
        }

    }

    public void setDues(String dues) throws ApiException {
        if (dues.matches("[+-]?\\d*(\\.\\d+)?")) {
            Integer intDues = Integer.parseInt(dues);
            switch (this.type) {
                case "CREDIT":
                    if (intDues < 0 && intDues <= 6)
                        throw new ApiException(HttpStatus.BAD_REQUEST, "Error: Se ha ingresado una cantidad de cuotas invalida.");
                    break;
                case "DEBIT":
                    if (intDues != 1)
                        throw new ApiException(HttpStatus.BAD_REQUEST, "Error: Se ha ingresado una cantidad de cuotas diferente a 1.");
                    break;
            }
            this.dues = intDues;
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error: La cantidad de cuotas debe ser un valor numérico.");
        }
    }
}
