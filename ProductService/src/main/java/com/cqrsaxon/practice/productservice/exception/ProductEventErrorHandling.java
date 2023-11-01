package com.cqrsaxon.practice.productservice.exception;

import jakarta.validation.constraints.NotNull;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

public class ProductEventErrorHandling implements ListenerInvocationErrorHandler {
    @Override
    public void onError(@NotNull Exception exception, @NotNull EventMessage<?> event, @NotNull EventMessageHandler eventHandler) throws Exception {
        throw exception;
    }
}
