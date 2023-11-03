package com.cqrsaxon.practice.productservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.axonframework.commandhandling.distributed.MetaDataRoutingStrategy;
import org.axonframework.commandhandling.distributed.RoutingStrategy;
import org.axonframework.commandhandling.distributed.UnresolvedRoutingKeyPolicy;
import org.axonframework.serialization.SerializedType;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {
    @Bean
    public RoutingStrategy routingStrategy() {
        return MetaDataRoutingStrategy.builder()
                .metaDataKey("my-routing-key")
                .fallbackRoutingStrategy(UnresolvedRoutingKeyPolicy.RANDOM_KEY)
                .build();
    }

//    @Bean
//    @Primary
//    @Qualifier("messageSerializer")
//    public Serializer serializer() {
//        return ShorterJacksonSerializer.builder()
//                .build();
//    }



}

