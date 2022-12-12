package com.tenpo.app;

import com.tenpo.external.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.math.*;
import java.util.concurrent.*;

@SpringBootTest
public class PercentageStorageTest {

    @Autowired
    PercentageStorage percentageStorage;

    @Test
    void test_percentage_storage_cached_value_with_empty_cache() throws ExecutionException {
        percentageStorage.setConfiguration(TimeUnit.MILLISECONDS, 200);

        NumberFormatException thrown = Assertions.assertThrows(NumberFormatException.class, () -> {
            BigDecimal percentage = percentageStorage.getPercentage();
        });
    }

    @Test
    void test_percentage_storage_cached_value_ok() throws ExecutionException {
        percentageStorage.savePercentage("10");
        BigDecimal percentage = percentageStorage.getPercentage();
        Assertions.assertTrue("10".equals(percentage.toString()));
    }

    @Test
    void test_percentage_storage_cached_value_expired() throws ExecutionException, InterruptedException {
        percentageStorage.setConfiguration(TimeUnit.MILLISECONDS, 200);
        percentageStorage.savePercentage("10");
        Thread.sleep(250);
        NumberFormatException thrown = Assertions.assertThrows(NumberFormatException.class, () -> {
            BigDecimal percentage = percentageStorage.getPercentage();
        });
    }
}
