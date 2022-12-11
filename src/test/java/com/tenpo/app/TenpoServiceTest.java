package com.tenpo.app;

import com.tenpo.external.*;
import com.tenpo.model.dto.*;
import com.tenpo.model.error.*;
import com.tenpo.service.*;
import org.aspectj.lang.annotation.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.math.*;
import java.util.concurrent.*;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TenpoServiceTest {

    @Mock
    PercentageStorage percentageStorage;

    @Mock
    PercentageService percentageMockedService;

    private TenpoService tenpoService;

    @BeforeEach
    void setUp(){
        tenpoService = new TenpoService(percentageMockedService, percentageStorage);
    }

    @Test
    public void test_external_api_max_tries() throws ExecutionException {

        PercentageAPIMaxTriesException thrown = Assertions.assertThrows(PercentageAPIMaxTriesException.class, () -> {
            when(percentageMockedService.getPercentage()).thenThrow(RuntimeException.class);
            tenpoService.getExternalPercentageToApplyWithRetries();
        });

    }

    @Test
    public void test_no_percentage_value_cached() throws ExecutionException {

        StoragedValueException thrown = Assertions.assertThrows(StoragedValueException.class, () -> {
            when(percentageMockedService.getPercentage()).thenThrow(RuntimeException.class);
            when(percentageStorage.getPercentage()).thenThrow(NumberFormatException.class);
            tenpoService.getPercentageToApply();
        });
    }

    @Test
    public void test_get_percentage_value_cached() throws ExecutionException {

        when(percentageMockedService.getPercentage()).thenThrow(RuntimeException.class);
        when(percentageStorage.getPercentage()).thenReturn(new BigDecimal(10));
        BigDecimal percentageToApply = tenpoService.getPercentageToApply();

        Assertions.assertEquals(percentageToApply, new BigDecimal(10));

    }

    @Test
    public void test_sum_numbers_with_percentage() throws ExecutionException {

        when(percentageMockedService.getPercentage()).thenReturn(new BigDecimal(10));
        PercentageSumDTO percentageSumDTO = tenpoService.sumNumbersWithPercentage(new BigDecimal(5), new BigDecimal(5));

        Assertions.assertEquals(percentageSumDTO.getResult(), new BigDecimal("11.0"));

    }
}
