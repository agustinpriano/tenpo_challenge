package com.tenpo.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ENDPOINT_CALL")
public class EndpointCall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "URL", nullable = false)
    private String url;

    @Column(name = "HTTP_METHOD", nullable = false)
    private String httpMethod;

    @Column(name = "STATUS_CODE")
    private String statusCode;

    @Column(name = "RESPONSE")
    private byte[] response;

    @Column(name = "ERROR")
    private String error;

    @Column(name = "DATE")
    private LocalDateTime date;

    public EndpointCall(Long id, String url, String httpMethod, String statusCode, byte[] response, String error) {
        this.id = id;
        this.url = url;
        this.httpMethod = httpMethod;
        this.statusCode = statusCode;
        this.response = response;
        this.error = error;
        this.date = LocalDateTime.now();
    }

    public EndpointCall() {

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

    public void setUrl(String endpoint) {
        this.url = endpoint;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
}
