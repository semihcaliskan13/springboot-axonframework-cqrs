package com.cqrsaxon.practice.productservice.coreapi;

import com.cqrsaxon.practice.productservice.collection.ProductLookUpCollection;
import com.cqrsaxon.practice.productservice.repository.ProductLookUpRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

import static java.util.Collections.singletonMap;

@Component
@Slf4j
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private final ProductLookUpRepository repository;

    public CreateProductCommandInterceptor(ProductLookUpRepository repository) {
        this.repository = repository;
    }

    @NotNull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@NotNull List<? extends CommandMessage<?>> messages) {

        return (index, command) -> {
            int lastIndexOfPackageName = command.getCommandName().lastIndexOf(".")+1;
            CommandMessage<Object> commandMessage = GenericCommandMessage.asCommandMessage(command)
                    .withMetaData(singletonMap("my-routing-key", command.getCommandName().substring(lastIndexOfPackageName)));
            log.info("Interceptor works!");
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
                ProductLookUpCollection product = repository.findByProductIdOrTitle(createProductCommand.getProductId(),
                        createProductCommand.getTitle());
                if (product != null) {
                    throw new IllegalStateException("Product already exist!");
                }
            }
            return commandMessage;
        };
    }
}
