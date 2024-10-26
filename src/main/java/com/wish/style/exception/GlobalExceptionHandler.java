package com.wish.style.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException (Exception ex){
        ProblemDetail errorDetail=null;
        if(ex instanceof BadCredentialsException){
             errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(401),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Authentication Failed");
        }
        else if (ex instanceof IllegalArgumentException) {
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Bad Request");
        }
        else if (ex instanceof MissingServletRequestParameterException) {
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Bad Request");
        }
        else if (ex instanceof AccessDeniedException) {
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Not Authorized");
        }
        else if(ex instanceof NoResourceFoundException){
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Resource Not Found!");
        }
        else if(ex instanceof SignatureException){
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Jwt Signature not valid!");
        }
        else if(ex instanceof ExpiredJwtException){
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Jwt Token Already Expired!");
        }
        else if(ex instanceof MalformedJwtException){
            errorDetail= ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Jwt Token Malformed!");
        }
        return errorDetail;
    }
}
