package com.tenpo.app;

import com.tenpo.model.dto.*;
import com.tenpo.model.entity.*;
import com.tenpo.model.error.*;
import com.tenpo.rest.controller.*;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.mock.web.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.context.request.*;

import java.io.*;
import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ApiRestTest {

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

    /*
    @Test
    public void test_max_api_calls_not_excedeed() {

        ResponseEntity<List<EndpointCallDTO>> allEndpointCalls = apiREST.getAllEndpointCalls(request, 0, 5);
        Assertions.assertNotNull(allEndpointCalls);
    }
*/

}
