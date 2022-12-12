package com.tenpo.app;

import com.tenpo.model.error.*;
import com.tenpo.rest.controller.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.mock.web.*;
import org.springframework.web.client.*;
import org.springframework.web.context.request.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TenpoRestControllerTest {

    @InjectMocks
    TenpoRestController controller;

    MockHttpServletRequest request;

    @BeforeEach
    void init(){
        request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void test_max_api_calls_not_excedeed() {
        controller.foo();
        controller.foo();
        String response = controller.foo();
        Assertions.assertTrue("".equals(response));
    }

    @Test
    public void test_max_api_calls_excedeed() {

        TooManyRequestException thrown = Assertions.assertThrows(TooManyRequestException.class, () -> {
            controller.foo();
            controller.foo();
            controller.foo();
            controller.foo();
        });
    }

}
