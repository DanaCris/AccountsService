package com.accounts.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableHystrix
public class CurrencyUtils {
    Logger logger = LoggerFactory.getLogger(CurrencyUtils.class);

    @Cacheable("exchangeRates")
    @HystrixCommand(fallbackMethod = "fallbackExchangeRates", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    })
    public JsonNode getExchangeRates() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rates = null;
        String exchangeRatesApiUrl = "https://api.exchangeratesapi.io/latest";

        logger.debug("Getting exchange rates from {}", exchangeRatesApiUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(exchangeRatesApiUrl, String.class);

        try {
            JsonNode root = mapper.readTree(response.getBody());
            rates = root.path("rates");
        } catch (JsonProcessingException e) {
            logger.error("Could not parse exchange rates JSON response");
        }
        return rates;
    }

    public JsonNode fallbackExchangeRates() {
        String exchangeRatesJson = "{\"CAD\":1.534,\"HKD\":8.4088,\"ISK\":151.7,\"PHP\":55.375,\"DKK\":7.4669,\"HUF\":353.07,\"CZK\":27.808,\"AUD\":1.8315,\"RON\":4.8357,\"SEK\":11.0888,\"IDR\":17701.2,\"INR\":82.358,\"BRL\":5.5116,\"RUB\":85.1561,\"HRK\":7.6118,\"JPY\":119.99,\"THB\":35.533,\"CHF\":1.0572,\"SGD\":1.5692,\"PLN\":4.6146,\"BGN\":1.9558,\"TRY\":7.011,\"CNY\":7.6549,\"NOK\":11.9745,\"NZD\":1.8676,\"ZAR\":19.0754,\"USD\":1.0843,\"MXN\":26.9302,\"ILS\":3.9379,\"GBP\":0.921,\"KRW\":1346.65,\"MYR\":4.804}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode exchangeRatesJsonNode = null;

        try {
            exchangeRatesJsonNode = objectMapper.readTree(exchangeRatesJson);
        } catch (JsonProcessingException e) {
            logger.error("Could not process JSON");
        }

        return exchangeRatesJsonNode;
    }
}
