package com.tenpo.service;

import com.tenpo.model.dto.EndpointCallDTO;
import com.tenpo.model.entity.EndpointCall;
import com.tenpo.repository.EndpointCallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EndpointCallService {

    @Autowired
    private EndpointCallRepository endpointCallRepository;


    public EndpointCall create (EndpointCallDTO dto){

        EndpointCall endpointCall = createEndpointCallObject(dto);

        return endpointCallRepository.save(endpointCall);
    }

    public List<EndpointCall> getAllEndpointCalls (){
        return endpointCallRepository.findAll();
    }

    public EndpointCall createEndpointCallObject(EndpointCallDTO dto){

        EndpointCall endpointCall = new EndpointCall();

        endpointCall.setHttpMethod(dto.getHttpMethod());
        endpointCall.setUrl(dto.getUrl());
        endpointCall.setStatusCode(dto.getStatusCode());
        endpointCall.setResponse(dto.getResponse());
        endpointCall.setDate(LocalDateTime.now());

        return endpointCall;
    }

}
