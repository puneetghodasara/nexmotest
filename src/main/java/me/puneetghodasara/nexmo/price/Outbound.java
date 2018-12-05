package me.puneetghodasara.nexmo.price;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Outbound {
    private String currency;
    private BigDecimal flatMobilePrice;

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getFlatMobilePrice() {
        return flatMobilePrice;
    }

    @Override
    public String toString() {
        return "Outbound{" +
                "currency='" + currency + '\'' +
                ", flatMobilePrice=" + flatMobilePrice +
                '}';
    }
}
