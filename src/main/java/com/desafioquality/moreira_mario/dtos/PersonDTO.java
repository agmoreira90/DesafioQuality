package com.desafioquality.moreira_mario.dtos;

import com.desafioquality.moreira_mario.exceptions.ApiException;
import com.desafioquality.moreira_mario.utils.UtilValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private String dni;
    private String name;
    private String lastName;
    private Date birthDate;
    private String mail;

    public void setBirthDate(String birthDate) throws ApiException {
        this.birthDate = UtilValidate.dateForamt(birthDate);
    }

    public void setMail(String mail) throws ApiException {
        this.mail = UtilValidate.emailFormat(mail);
    }

    public String getBirthDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.birthDate);
    }


}
