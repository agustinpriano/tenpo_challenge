package com.tenpo.external;

import com.google.common.cache.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.math.*;

@Component
@Scope(value = "singleton")
public class PercentageMockedService extends PercentageService{

    @Override
    public BigDecimal getPercentage() { // Esto debería poder devolver una excepcion
        return new BigDecimal(10);
    }
}
