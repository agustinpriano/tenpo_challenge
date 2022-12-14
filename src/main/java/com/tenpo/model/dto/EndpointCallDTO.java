package com.tenpo.model.dto;


import com.tenpo.model.entity.EndpointCall;

import java.time.LocalDateTime;

public class EndpointCallDTO {

    private Long id;
    private String url;
    private String httpMethod;
    private String statusCode;
    private byte[] response;
    private String error;
    private LocalDateTime date;

    public EndpointCallDTO(){

    }

    public EndpointCallDTO(Long id, String url, String httpMethod, String statusCode, byte[] response, String error) {
        this.id = id;
        this.url = url;
        this.httpMethod = httpMethod;
        this.statusCode = statusCode;
        this.response = response;
        this.error = error;
        this.date = LocalDateTime.now();
    }

    public EndpointCallDTO(String url, String httpMethod, String statusCode, byte[] response, String error) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.statusCode = statusCode;
        this.response = response;
        this.error = error;
        this.date = LocalDateTime.now();
    }

    public static EndpointCallDTO fromEntity(EndpointCall endpointCall){
        return new EndpointCallDTO(endpointCall.getId(), endpointCall.getUrl(), endpointCall.getHttpMethod(), endpointCall.getStatusCode(), endpointCall.getResponse(), endpointCall.getError());
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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getResponse() {
        return response;
    }

    public void setResponse(byte[] response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
