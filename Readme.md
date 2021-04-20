# Desafio Quality

El Desafio de Quality se trata de crear APIs correspondientes para lograr la consulta y reserva tanto de hoteles como de vuelos para una agencia de turismo.

## Usage

### Consulta de Hoteles

Endpoint: [/api/v1/hotels](http://localhost:8080/api/v1/hotels)
```
# Parametros
&"hotelCode"=                
&"name"=
&"city"=
&"roomType"=
&"price"=
&"availabilityFrom"=
&"availabilityTo"=
```

### Consulta de Vuelos

Endpoint: [/api/v1/flights](http://localhost:8080/api/v1/flights)
```
# Parametros
"flightNum"=
"flightOrigin"=
"flightDestination"=
"seatType"=
"price"=
"flightDeparture"=
"flightArrival"=
```

### Reserva de Hotel

Endpoint: [/api/v1/booking](http://localhost:8080/api/v1/booking)

```
{
    "userName" : "seba_gonzalez@unmail.com",
    "booking" : {
        "dateFrom" : "20/02/2021",
        "dateTo" : "28/02/2021",
        "destination" : "Buenos Aires",
        "hotelCode" : "BH-0002",
        "peopleAmount" : 2,
        "roomType" : "DOBLE",
        "people" : [
            {
                "dni" : "12345678",
                "name" : "Pepito",
                "lastName" : "Gomez",
                "birthDate" : "10/11/1982",
                "mail" : "pepitogomez@gmail.com"
            },
             {
                "dni" : "13345678",
                "name" : "Fulanito",
                "lastName" : "Gomez",
                "birthDate" : "10/11/1983",
                "mail" : "fulanitogomez@gmail.com"
            }
        ],
        "paymentMethod" : {
            "type" : "CREDIT",
            "number" : "1234-1234-1234-1234",
            "dues" : 3
        }
    }
}
```

### Reserva de Vuelos

Endpoint: [/api/v1/flight-reservation](http://localhost:8080/api/v1/flight-reservation)
```
{
    "userName" : "seba_gonzalez@unmail.com",
    "flightReservation" : {
        "dateFrom" : "10/02/2021",
        "dateTo" : "15/02/2021",
        "origin" : "Buenos Aires",
        "destination" : "Puerto Iguazú",
        "flightNumber" : "BAPI-1235",
        "seats":2,
        "seatType" : "Economy",
        "people" : [
            {
                "dni" : "12345678",
                "name" : "Pepito",
                "lastName" : "Gomez",
                "birthDate" : "10/11/1982",
                "mail" : "pepitogomez@gmail.com"
            },
             {
                "dni" : "13345678",
                "name" : "Fulanito",
                "lastName" : "Gomez",
                "birthDate" : "10/11/1983",
                "mail" : "fulanitogomez@gmail.com"
            }
        ],
        "paymentMethod" : {
            "type" : "DEBIT",
            "number" : "1234-1234-1234-1234",
            "dues" : 1
        }
    }
}
```