package com.desafioquality.moreira_mario.utils;

import com.desafioquality.moreira_mario.dtos.*;
import com.desafioquality.moreira_mario.exceptions.ApiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UtilTest {
    private static SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");

    public static BookingRequestDTO createRequest(String cardType, Integer dues) throws ApiException, ParseException {

        sdf.setLenient(false);
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setUserName("agustin.moreira@mercadolibre.com");
        BookingInDTO bookingIn = new BookingInDTO();
        bookingIn.setDateFrom("20/02/2021");
        bookingIn.setDateTo("28/02/2021");
        bookingIn.setDestination("Buenos Aires");
        bookingIn.setHotelCode("BH-0002");
        bookingIn.setPeopleAmount("2");
        bookingIn.setRoomType("DOBLE");
        PersonDTO person1 = new PersonDTO("12345678", "Pepito", "Gomez", sdf.parse("10/11/1982"), "pepitogomez@gmail.com");
        PersonDTO person2 = new PersonDTO("13345678", "Fulanito", "Gomez", sdf.parse("11/11/1983"), "fulanitogomez@gmail.com");
        List<PersonDTO> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        bookingIn.setPeople(persons);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        switch (cardType){
            case "CREDIT":
                switch (dues) {
                    case 1:
                    case 2:
                    case 3:
                        paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", dues);
                        break;
                    case 4:
                    case 5:
                    case 6:
                        paymentMethod = new PaymentMethodDTO("CREDIT", "1234-1234-1234-1234", dues);
                        break;
                }
                break;
            case "DEBIT":
                paymentMethod = new PaymentMethodDTO("DEBIT", "1234-1234-1234-1234", dues);
                break;
        }
        bookingIn.setPaymentMethod(paymentMethod);
        bookingRequest.setBooking(bookingIn);
        return bookingRequest;
    }

    public static BookingResponseDTO createResponse(String cardType, Integer dues) throws ApiException, ParseException {
        sdf.setLenient(false);
        BookingResponseDTO bookingResponse = new BookingResponseDTO();
        bookingResponse.setUserName("agustin.moreira@mercadolibre.com");
        BookingOutDTO bookingOutDTO = new BookingOutDTO();
        bookingOutDTO.setDateFrom("20/02/2021");
        bookingOutDTO.setDateTo("28/02/2021");
        bookingOutDTO.setDestination("Buenos Aires");
        bookingOutDTO.setHotelCode("BH-0002");
        bookingOutDTO.setPeopleAmount(2);
        bookingOutDTO.setRoomType("DOBLE");
        PersonDTO person1 = new PersonDTO("12345678", "Pepito", "Gomez", sdf.parse("10/11/1982"), "pepitogomez@gmail.com");
        PersonDTO person2 = new PersonDTO("13345678", "Fulanito", "Gomez", sdf.parse("11/11/1983"), "fulanitogomez@gmail.com");
        List<PersonDTO> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        bookingOutDTO.setPeople(persons);
        bookingResponse.setBooking(bookingOutDTO);
        bookingResponse = calculatePrice(bookingResponse,cardType,dues);
        StatusCodeDTO statusCode = new StatusCodeDTO(200, "El proceso termino satisfactoriamente");
        bookingResponse.setStatusCode(statusCode);
        return bookingResponse;
    }

    public static BookingResponseDTO calculatePrice(BookingResponseDTO bookingResponse,String cardType, Integer dues){
        switch (cardType){
            case "CREDIT":
                switch (dues) {
                    case 1:
                    case 2:
                    case 3:
                        bookingResponse.setTotal(Double.parseDouble("7560"));
                        bookingResponse.setInterest(Double.parseDouble("5"));
                        bookingResponse.setAmount(Double.parseDouble("7200"));
                        break;
                    case 4:
                    case 5:
                    case 6:
                        bookingResponse.setTotal(Double.parseDouble("7920"));
                        bookingResponse.setInterest(Double.parseDouble("10"));
                        bookingResponse.setAmount(Double.parseDouble("7200"));
                        break;
                }
                break;
            case "DEBIT":
                bookingResponse.setTotal(Double.parseDouble("7200"));
                bookingResponse.setInterest(Double.parseDouble("0"));
                bookingResponse.setAmount(Double.parseDouble("7200"));
                break;
        }
        return bookingResponse;
    }
}
