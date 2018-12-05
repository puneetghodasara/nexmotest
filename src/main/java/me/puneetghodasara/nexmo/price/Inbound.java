package me.puneetghodasara.nexmo.price;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Inbound {


    private List<NumberPricingInfo> numbers = new ArrayList<>();

    public List<NumberPricingInfo> getNumbers() {
        return numbers;
    }
}
