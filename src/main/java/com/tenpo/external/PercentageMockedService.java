package com.tenpo.external;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.math.*;

@Component
@Scope(value = "singleton")
public class PercentageMockedService extends PercentageService{

    @Override
    public BigDecimal getPercentage() throws RuntimeException{
        return new BigDecimal(10);
    }
}
