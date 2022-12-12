package com.tenpo.app;

import com.tenpo.model.entity.*;
import com.tenpo.repository.*;
import com.tenpo.service.*;
import org.aspectj.lang.annotation.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

@SpringBootTest
public class EndpointCallServiceTest {

    private EndpointCall endpointCall1;
    private EndpointCall endpointCall2;
    private EndpointCall endpointCall3;

    @Autowired
    EndpointCallRepository repo;

    @Autowired
    EndpointCallService endpointCallService;

    @BeforeEach
    void setUp(){
        endpointCall1 = new EndpointCall();
        endpointCall1.setId((long) 1);
        endpointCall2 = new EndpointCall();
        endpointCall2.setId((long) 2);
    }

    @Test
    void test_create(){
        repo.save(endpointCall1);
        Assertions.assertNotNull(repo.findById((long) 1));
    }

    @Test
    void test_read_all_with_pagination(){
        repo.save(endpointCall1);
        repo.save(endpointCall2);

        List<EndpointCall> endpointCalls = endpointCallService.getAllEndpointCalls(0, 2);
        Assertions.assertTrue(endpointCalls.size() == 2);
    }

    @Test
    void test_read_some_with_pagination(){
        repo.save(endpointCall1);
        repo.save(endpointCall2);

        List<EndpointCall> endpointCalls = endpointCallService.getAllEndpointCalls(0, 1);
        Assertions.assertTrue(endpointCalls.size() == 1);
    }
}
