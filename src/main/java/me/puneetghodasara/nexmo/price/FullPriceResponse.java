package me.puneetghodasara.nexmo.price;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexmo.client.NexmoResponseParseException;
import com.nexmo.client.account.Country;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FullPriceResponse {

    private List<Country> countries = new ArrayList<>();

    public List<Country> getCountries() {
        return countries;
    }

    public static FullPriceResponse fromJson(final String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, FullPriceResponse.class);
        } catch (IOException e) {
            throw new NexmoResponseParseException("Failed to produce FullPriceResponse from json.", e);
        }
    }
}
