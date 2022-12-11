package com.tenpo.app;

import com.tenpo.model.entity.*;
import com.tenpo.repository.*;
import com.tenpo.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EndpointCallServiceTest {

    @Autowired
    EndpointCallRepository repo;

    @Autowired
    EndpointCallService endpointCallService;

    @Test
    @Order(1)
    void test_create(){
        EndpointCall endpointCall1 = new EndpointCall();
        endpointCall1.setId((long) 1);
        repo.save(endpointCall1);
        Assertions.assertNotNull(repo.findById((long) 1));
    }

    @Test
    @Order(2)
    void test_read_all_with_pagination(){
        EndpointCall endpointCall2 = new EndpointCall();
        endpointCall2.setId((long) 2);

        EndpointCall endpointCall3 = new EndpointCall();
        endpointCall3.setId((long) 3);

        repo.save(endpointCall2);
        repo.save(endpointCall3);

        List<EndpointCall> endpointCalls = endpointCallService.getAllEndpointCalls(0, 3);
        Assertions.assertTrue(endpointCalls.size() == 3);
    }

    @Test
    @Order(3)
    void test_read_some_with_pagination(){
        List<EndpointCall> endpointCalls = endpointCallService.getAllEndpointCalls(0, 2);
        Assertions.assertTrue(endpointCalls.size() == 2);
    }
}
