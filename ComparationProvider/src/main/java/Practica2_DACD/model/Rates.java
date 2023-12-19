package Practica2_DACD.model;

public class Rates {
    private final String code;
    private final String name;
    private final double rate;
    private final double tax;

    public Rates(String code, String name, double rate, double tax) {
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.tax = tax;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public double getTax() {
        return tax;
    }
}
