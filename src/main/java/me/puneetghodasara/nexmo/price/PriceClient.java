package me.puneetghodasara.nexmo.price;

import com.nexmo.client.HttpWrapper;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.account.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PriceClient {

    private static final Logger logger = LoggerFactory.getLogger(PriceClient.class);

    private final PriceEndpoint priceEndpoint;
    private final FullPriceEndpoint fullPriceEndpoint;

    public PriceClient(final HttpWrapper httpWrapper) {
        priceEndpoint = new PriceEndpoint(httpWrapper);
        this.fullPriceEndpoint = new FullPriceEndpoint(httpWrapper);
    }

    public Optional<PriceResponse> getPrice(final String country) {
        try {
            return Optional.ofNullable(priceEndpoint.execute(new PriceRequest(country)));
        } catch (IOException | NexmoClientException e) {
            logger.warn("Error to fetch price for country {}", country);
            System.out.println("Error to fetch price for country " + country);
            return Optional.empty();
        }
    }

    public List<Country> getCountries(){
        try {
            return fullPriceEndpoint.execute(null).getCountries();
        } catch (IOException | NexmoClientException e) {
            logger.warn("Error to fetch price for countries");
            System.out.println("Error to fetch countries ");
            return Collections.emptyList();
        }
    }

}
