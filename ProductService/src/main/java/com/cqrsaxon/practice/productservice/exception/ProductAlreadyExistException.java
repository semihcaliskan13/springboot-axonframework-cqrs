package com.cqrsaxon.practice.productservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;


public record ProductAlreadyExistException (Date timeStamp, String message, Integer statusCode){}


