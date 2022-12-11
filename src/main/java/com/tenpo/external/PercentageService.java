package com.tenpo.external;

import java.math.*;

public abstract class PercentageService {

    private PercentageStorage percentageStorage;
    public abstract BigDecimal getPercentage() throws RuntimeException;
}
