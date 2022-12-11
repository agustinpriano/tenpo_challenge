package com.tenpo.app;

import com.tenpo.external.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.math.*;
import java.util.concurrent.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PercentageStorageTest {

    @Autowired
    PercentageStorage percentageStorage;

    @Test
    @Order(1)
    void test_percentage_storage_not_found() throws ExecutionException {
        NumberFormatException thrown = Assertions.assertThrows(NumberFormatException.class, () -> {
            BigDecimal percentage = percentageStorage.getPercentage();
        });
    }

    @Test
    @Order(2)
    void test_percentage_storage_ok() throws ExecutionException {
        percentageStorage.savePercentage("10");
        BigDecimal percentage = percentageStorage.getPercentage();
        Assertions.assertTrue("10".equals(percentage.toString()));
    }

    @Test
    @Order(3)
    void test_percentage_storage_expiration() throws ExecutionException, InterruptedException {
        percentageStorage.setConfiguration(TimeUnit.MILLISECONDS, 200);
        percentageStorage.savePercentage("10");
        Thread.sleep(250);
        NumberFormatException thrown = Assertions.assertThrows(NumberFormatException.class, () -> {
            BigDecimal percentage = percentageStorage.getPercentage();
        });
    }
}
