package me.puneetghodasara.nexmo.price;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexmo.client.NexmoResponseParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PriceResponse {

    private String dialingPrefix;
    private String country;
    private Messaging messaging;

    public static final Function<Messaging, Double> HIGHEST_PRICED_RENTED_PHONE = m -> m.getInbound().getNumbers()
            .stream()
            .filter(numberPricingInfo -> numberPricingInfo.getFeatures().contentEquals("SMS,VOICE"))
            .map(NumberPricingInfo::getMonthlyFee)
            .mapToDouble(BigDecimal::doubleValue)
            .max()
            .orElse(Double.MIN_VALUE);

    public static final Predicate<Messaging> HAS_TOLLFREE_NUMBER = m -> m.getInbound().getNumbers()
            .stream()
            .anyMatch(NumberPricingInfo.IS_TOLLFREE);


    public static PriceResponse fromJson(final String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, PriceResponse.class);
        } catch (IOException e) {
            throw new NexmoResponseParseException("Failed to produce PriceResponse from json.", e);
        }
    }


    public String getCountry() {
        return country;
    }

    public Messaging getMessaging() {
        return messaging;
    }

    public Optional<Inbound> getInbound() {
        return Optional.ofNullable(messaging.getInbound());
    }

    public Optional<Outbound> getOutbound() {
        return Optional.ofNullable(messaging.getOutbound());
    }

    public Optional<Double> getHighestMonthlyFeeNumber() {
        return Stream.of(messaging).map(HIGHEST_PRICED_RENTED_PHONE).findFirst();
    }

    public boolean hasTollFree() {
        return Stream.of(messaging)
                .anyMatch(HAS_TOLLFREE_NUMBER);
    }

    @Override
    public String toString() {
        return "PriceResponse{" +
                "dialingPrefix='" + dialingPrefix + '\'' +
                ", country=" + country +
                ", messaging=" + messaging +
                '}';
    }
}


