package me.puneetghodasara.nexmo.price;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.function.Predicate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NumberPricingInfo {


    public static final Predicate<NumberPricingInfo> IS_TOLLFREE = num -> num.getType().contentEquals("tollfree");

    private String features;
    private String type;
    private BigDecimal monthlyFee;
    private String currency;

    public String getFeatures() {
        return features;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "NumberPricingInfo{" +
                "features='" + features + '\'' +
                ", type='" + type + '\'' +
                ", monthlyFee=" + monthlyFee +
                ", currency='" + currency + '\'' +
                '}';
    }
}
