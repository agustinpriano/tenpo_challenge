package com.tenpo.rest.controller;

import com.tenpo.model.dto.*;
import com.tenpo.model.error.*;
import com.tenpo.service.EndpointCallService;
import com.tenpo.service.SumService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class TenpoRestController {

    public final Bucket bucket;

    public final Integer apiCallsPerMinute = 3;

    public TenpoRestController() {
        Bandwidth limit = Bandwidth.classic(apiCallsPerMinute, Refill.greedy(apiCallsPerMinute, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @Autowired
    private EndpointCallService endpointCallService;

    @Autowired
    private SumService sumService;

    @GetMapping("endpointCall")
    private ResponseEntity<List<EndpointCallDTO>> getAllEndpointCalls(
            HttpServletRequest request,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        if(validateMaxRequestPerMinute()){
            throw new TooManyRequestException("Se superó el límite de " + apiCallsPerMinute + " requests por minuto para la API");
        }

        ResponseEntity<List<EndpointCallDTO>> response = ResponseEntity.ok(endpointCallService.getAllEndpointCalls(offset, pageSize).stream().map(EndpointCallDTO::fromEntity).collect(Collectors.toList()));

        endpointCallService.create(new EndpointCallDTO(request.getRequestURI(), request.getMethod(), response.getStatusCode().toString(), response.getBody().toString().getBytes(), ""));

        return response;
    }

    @GetMapping("{number1}/{number2}/sum_with_percentage")
    private ResponseEntity<PercentageSumDTO> sumNumbersWithPercentage(
            @PathVariable("number1") BigDecimal number1,
            @PathVariable("number2") BigDecimal number2,
            HttpServletRequest request) {

        if (validateMaxRequestPerMinute()) {
            throw new TooManyRequestException("Se superó el límite de " + apiCallsPerMinute + " requests por minuto para la API");
        }

        try{
            ResponseEntity<PercentageSumDTO> response = ResponseEntity.ok(sumService.sumNumbersWithPercentage(number1, number2));
            endpointCallService.create(new EndpointCallDTO(request.getRequestURI(), request.getMethod(), response.getStatusCode().toString(), response.getBody().toString().getBytes(), ""));

            return response;

        }catch(StoragedValueException e){
            endpointCallService.create(new EndpointCallDTO(request.getRequestURI(), request.getMethod(), "500", "".getBytes(), e.getMessage()));
            throw new APIExecutionError(e.getMessage());
        }
    }

    @GetMapping("foo")
    public String foo() {

        if(validateMaxRequestPerMinute()){
            throw new TooManyRequestException("Se superó el límite de " + apiCallsPerMinute + " requests por minuto para la API");
        }
        return "";
    }

    private boolean validateMaxRequestPerMinute() {
        if (!bucket.tryConsume(1)) {
            return true;
        }
        return false;
    }


}
