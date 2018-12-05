package me.puneetghodasara.nexmo.price;

public class PriceRequest {

    private final String country;

    public PriceRequest(final String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
