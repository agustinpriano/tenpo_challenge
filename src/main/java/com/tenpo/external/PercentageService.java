package com.tenpo.external;
import org.springframework.stereotype.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.math.*;

public abstract class PercentageService {

    private PercentageStorage percentageStorage;
    public abstract BigDecimal getPercentage() throws RuntimeException;
}
