package com.tenpo.app;

import com.tenpo.external.*;
import com.tenpo.model.dto.*;
import com.tenpo.model.error.*;
import com.tenpo.service.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.web.client.*;

import java.math.*;
import java.util.concurrent.*;

import static org.mockito.Mockito.when;

@SpringBootTest
public class SumServiceTest {

    @Mock
    PercentageStorage percentageStorage;

    @Mock
    PercentageAPIService percentageAPIService;

    private SumService sumService;

    @BeforeEach
    void setUp(){
        sumService = new SumService(percentageAPIService, percentageStorage);
    }

    @Test
    public void test_external_api_max_tries() throws ExecutionException {

        PercentageAPIMaxTriesException thrown = Assertions.assertThrows(PercentageAPIMaxTriesException.class, () -> {
            when(percentageAPIService.getPercentage()).thenThrow(RestClientException.class);
            sumService.getExternalPercentageToApplyWithRetries();
        });
    }


    @Test
    public void test_no_percentage_value_cached() throws ExecutionException {

        StoragedValueException thrown = Assertions.assertThrows(StoragedValueException.class, () -> {
            when(sumService.getExternalPercentageToApplyWithRetries()).thenThrow(PercentageAPIMaxTriesException.class);
            when(percentageStorage.getPercentage()).thenThrow(NumberFormatException.class);
            sumService.getPercentageToApply();
        });
    }

    @Test
    public void test_get_percentage_value_cached() throws ExecutionException {

        when(sumService.getExternalPercentageToApplyWithRetries()).thenThrow(PercentageAPIMaxTriesException.class);
        when(percentageStorage.getPercentage()).thenReturn(new BigDecimal(10));
        BigDecimal percentageToApply = sumService.getPercentageToApply();

        Assertions.assertEquals(percentageToApply, new BigDecimal(10));

    }

    @Test
    public void test_sum_numbers_with_percentage() throws ExecutionException {

        when(percentageAPIService.getPercentage()).thenReturn(new BigDecimal(10));
        PercentageSumDTO percentageSumDTO = sumService.sumNumbersWithPercentage(new BigDecimal(5), new BigDecimal(5));

        Assertions.assertEquals(percentageSumDTO.getResult(), new BigDecimal("11.0"));

    }

}
