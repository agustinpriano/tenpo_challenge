package com.tenpo.rest.handler;

import com.tenpo.model.error.*;
import org.springframework.beans.*;
import org.springframework.core.*;
import org.springframework.core.annotation.*;
import org.springframework.http.*;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.sql.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Invalid HTTP method for this endpoint";
        return buildResponseEntity(new APIError(HttpStatus.METHOD_NOT_ALLOWED, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Path Params input types not valid";
        return buildResponseEntity(new APIError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(TooManyRequestException.class)
    protected ResponseEntity<Object> handleTooManyRequests(TooManyRequestException ex) {
        String error = ex.getMessage();
        return buildResponseEntity(new APIError(HttpStatus.TOO_MANY_REQUESTS, error, ex));
    }

    @ExceptionHandler(StoragedValueException.class)
    protected ResponseEntity<Object> handleStoragedValueException(StoragedValueException ex) {
        String error = "API Error: The service couldn't apply any percentage value";
        return buildResponseEntity(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Object> handleSQLException(SQLException ex) {
        String error = ex.getMessage();
        return buildResponseEntity(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(APIError apiError) {
        return new ResponseEntity(apiError, apiError.getStatus());
    }

}
