package com.tenpo.service;

import com.tenpo.model.dto.EndpointCallDTO;
import com.tenpo.model.entity.EndpointCall;
import com.tenpo.repository.EndpointCallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

@Service
public class EndpointCallService {

    @Autowired
    private EndpointCallRepository endpointCallRepository;

    @Autowired
    private Executor executor;

    @Async
    public CompletableFuture<EndpointCall> create (EndpointCallDTO dto){
        return CompletableFuture.supplyAsync( () -> {
            return endpointCallRepository.save(createEndpointCallObject(dto));
        }, executor).exceptionally( ex -> {
            return null;
        });
    }

    public List<EndpointCall> getAllEndpointCalls (Integer offset, Integer pageSize){
        return endpointCallRepository.findAll(PageRequest.of(offset, pageSize)).getContent();
    }

    public EndpointCall createEndpointCallObject(EndpointCallDTO dto){

        EndpointCall endpointCall = new EndpointCall();

        endpointCall.setResponse(dto.getResponse());
        endpointCall.setError(dto.getError());
        endpointCall.setHttpMethod(dto.getHttpMethod());
        endpointCall.setUrl(dto.getUrl());
        endpointCall.setStatusCode(dto.getStatusCode());
        endpointCall.setDate(LocalDateTime.now());

        return endpointCall;
    }

}
