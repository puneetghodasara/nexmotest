package me.puneetghodasara.nexmo;

import com.nexmo.client.account.Country;
import me.puneetghodasara.nexmo.client.ModifiedNexmoClient;
import me.puneetghodasara.nexmo.price.Messaging;
import me.puneetghodasara.nexmo.price.Outbound;
import me.puneetghodasara.nexmo.price.PriceClient;
import me.puneetghodasara.nexmo.price.PriceResponse;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Driver {

    public static void main(String[] args) throws Exception {


//        final String[] isoCountries = Locale.getISOCountries();
//        Arrays.stream(isoCountries).forEach(System.out::println);

        // NOTE : Do not hard code or expose api-key and api-secret
        final ModifiedNexmoClient modifiedNexmoClient = new ModifiedNexmoClient.Builder()
                .apiKey("8f90f30e")
                .apiSecret("tfGagpCuHyGHy1Cq")
                .build();
        final PriceClient priceClient = modifiedNexmoClient.getPrice();

        /**
         * Get all countries
         */
        final List<String> countries = priceClient.getCountries().stream()
                .map(Country::getCode)
                .collect(Collectors.toList());


        final long totalCountries = countries.stream().count();
        System.out.println("We found " + totalCountries + " countries");

        /**
         * Fetch price JSON for each country
         */
        final List<PriceResponse> priceResponses = countries
                .stream()
                .map(priceClient::getPrice)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        /**
         * Task 1 : Find Average price
         */
        final double averagePrice = priceResponses.stream()
                .map(PriceResponse::getMessaging)
                .map(Messaging::getOutbound)
                .filter(outbound -> outbound.getFlatMobilePrice() != null)
                .map(Outbound::getFlatMobilePrice)
                .mapToDouble(BigDecimal::doubleValue)
//                .peek(value -> System.out.println("value = " + value))
                .average()
                .orElse(0);

        System.out.println("Average Price is " + averagePrice);

        /**
         * Task 2 : Expensive Incoming SMS+VOICE is at
         */
        final Map<String, Double> countryPriceMap = priceResponses
                .stream()
                .collect(Collectors.toMap(PriceResponse::getCountry, priceResponse -> priceResponse.getHighestMonthlyFeeNumber().orElse(0d)));
        final String country = countryPriceMap.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(Exception::new)
                .getKey();
        System.out.println("Expensive Incoming SMS+VOICE is at " + country);

        /**
         * Task 3 Country where inbound tollfree available
         */
        final List<String> countriesWithTollFree = priceResponses.stream()
                .filter(PriceResponse::hasTollFree)
                .map(PriceResponse::getCountry)
                .collect(Collectors.toList());
        System.out.println("Country where inbound tollfree available " + countriesWithTollFree);
    }
}
