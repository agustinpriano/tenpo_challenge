package com.tenpo.external;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.math.*;

@Component
@Scope(value = "singleton")
public class PercentageAPIService {

    private final String randomAPIUrl = "http://www.randomnumberapi.com/api/v1.0/random?min=1&max=100";

    public BigDecimal getPercentage() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Integer[]> response
                = restTemplate.getForEntity(randomAPIUrl, Integer[].class);

        return new BigDecimal(response.getBody()[0]);
    }
}
