package com.tenpo.rest.controller;

import com.tenpo.model.dto.EndpointCallDTO;
import com.tenpo.model.error.*;
import com.tenpo.service.EndpointCallService;
import com.tenpo.service.TenpoService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class ApiREST {

    // TODO: 7/12/2022
    /*
        Cosas que faltan hacer:
        - Manejar errores de mejor forma (INTERNAL_SERVER_ERROR)
        -
     */
    public final Bucket bucket;

    public final Integer apiCallsPerMinute = 3;

    public ApiREST() {
        Bandwidth limit = Bandwidth.classic(apiCallsPerMinute, Refill.greedy(apiCallsPerMinute, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @Autowired
    private EndpointCallService endpointCallService;

    @Autowired
    private TenpoService tenpoService;

    @GetMapping("endpointCall")
    private ResponseEntity<List<EndpointCallDTO>> getAllEndpointCalls(HttpServletRequest request) {

        if(validateMaxRequestPerMinute()){
            throw new TooManyRequestException("Se superó el límite de " + apiCallsPerMinute + " requests por minuto para la API");
        }

        ResponseEntity<List<EndpointCallDTO>> response = ResponseEntity.ok(endpointCallService.getAllEndpointCalls().stream().map(EndpointCallDTO::fromEntity).collect(Collectors.toList()));

        endpointCallService.create(new EndpointCallDTO(request.getRequestURI(), request.getMethod(), response.getStatusCode().toString(), response.toString().getBytes()));

        return response;
    }

    @GetMapping("{number1}/{number2}/sum_with_percentage")
    private ResponseEntity<String> sumNumbersWithPercentage(
            @PathVariable("number1") BigDecimal number1,
            @PathVariable("number2") BigDecimal number2,
            HttpServletRequest request) {

        if (!bucket.tryConsume(1)) {
            throw new TooManyRequestException("Se superó el límite de " + apiCallsPerMinute + " requests por minuto para la API");
        }

        ResponseEntity<String> response = ResponseEntity.ok("Sum with percentage applied: " + tenpoService.sumNumbersWithPercentage(number1, number2).toString());

        endpointCallService.create(new EndpointCallDTO(request.getRequestURI(), request.getMethod(), response.getStatusCode().toString(), response.toString().getBytes()));

        return response;
    }

    private boolean validateMaxRequestPerMinute() {
        if (!bucket.tryConsume(1)) {
            return true;
        }
        return false;
    }


}
