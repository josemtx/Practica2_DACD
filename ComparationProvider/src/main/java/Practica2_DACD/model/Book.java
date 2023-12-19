package Practica2_DACD.model;

import java.time.LocalDate;

public class Book {
    private final String hotelKey;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Book(String hotelKey, LocalDate checkIn, LocalDate checkOut) {
        this.hotelKey = hotelKey;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getHotel_Key() {
        return hotelKey;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }
}
