package Practica2_DACD.model;

import java.sql.Timestamp;
import java.time.Instant;

public class Weather {
    private final Instant predictionTime;
    private final Timestamp ts;
    private final String ss;
    private final double temp;
    private final double pop;
    private final double humidity;
    private final double clouds;
    private final double windSpeed;
    private final Location Location;

    public Weather(Instant predictionTime, Timestamp ts, String ss, double temp, double pop, double humidity, double clouds, double windSpeed, Location locations) {
        this.predictionTime = predictionTime;
        this.ts = ts;
        this.ss = ss;
        this.temp = temp;
        this.pop = pop;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.Location = locations;
    }

    public Instant getTs() {
        return predictionTime;
    }

    public double getTemp() {
        return temp;
    }

    public double getPop() {
        return pop;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getClouds() {
        return clouds;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public Location getLocation() {
        return Location;
    }
}