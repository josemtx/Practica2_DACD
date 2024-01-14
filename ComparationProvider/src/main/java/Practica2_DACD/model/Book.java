package Practica2_DACD.model;

import java.time.LocalDate;

public class Book {
    private final String hotelName;
    private final String hotelKey;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final Location location;


    public Book(String hotelName, String hotelKey, LocalDate checkIn, LocalDate checkOut, Location location) {
        this.hotelName = hotelName;
        this.hotelKey = hotelKey;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.location = location;
    }

    public String getHotel_Key() {
        return hotelKey;
    }

    public String getName() {
        return hotelName;
    }

    public Location getLocation() {
        return location;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public String getHotelKey() {
        return hotelKey;
    }
}
