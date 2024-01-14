package Practica2_DACD;

public class DestinationInfo {
    private String hotelName;
    private double averageRate;
    private String currency;
    private double averageTemperature;
    private double probabilityOfPrecipitation;

    public DestinationInfo(String hotelName, double averageRate, String currency, double averageTemperature, double probabilityOfPrecipitation) {
        this.hotelName = hotelName;
        this.averageRate = averageRate;
        this.currency = currency;
        this.averageTemperature = averageTemperature;
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public double getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
    }

    public void setProbabilityOfPrecipitation(double probabilityOfPrecipitation) {
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
    }

}