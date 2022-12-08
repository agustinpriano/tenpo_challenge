package com.tenpo.model.dto;


import com.tenpo.model.entity.EndpointCall;
import jakarta.persistence.Column;
import jakarta.websocket.Endpoint;

import java.time.LocalDateTime;

public class EndpointCallDTO {

    private Long id;
    private String url;
    private String httpMethod;
    private String statusCode;
    private byte[] response;
    private LocalDateTime date;

    public EndpointCallDTO(Long id, String url, String httpMethod, String statusCode, byte[] response, LocalDateTime date) {
        this.id = id;
        this.url = url;
        this.httpMethod = httpMethod;
        this.statusCode = statusCode;
        this.response = response;
        this.date = date;
    }

    public EndpointCallDTO(String url, String httpMethod, String statusCode, byte[] response) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.statusCode = statusCode;
        this.response = response;
        this.date = LocalDateTime.now();
    }

    public static EndpointCallDTO fromEntity(EndpointCall endpointCall){
        return new EndpointCallDTO(endpointCall.getId(), endpointCall.getUrl(), endpointCall.getHttpMethod(), endpointCall.getStatusCode(), endpointCall.getResponse(), endpointCall.getDate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public byte[] getResponse() {
        return response;
    }

    public void setResponse(byte[] response) {
        this.response = response;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
