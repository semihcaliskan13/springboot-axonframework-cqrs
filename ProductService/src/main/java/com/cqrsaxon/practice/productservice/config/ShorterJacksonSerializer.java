package com.cqrsaxon.practice.productservice.config;

import jakarta.validation.constraints.NotNull;
import org.axonframework.serialization.SerializedType;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.json.JacksonSerializer;

public class ShorterJacksonSerializer extends JacksonSerializer {

    protected ShorterJacksonSerializer(Builder builder) {
        super(builder);
    }

    String basePackage = "com.cqrsaxon.practice.productservice.coreapi";

    protected String resolveClassName(SerializedType serializedType) {
        return basePackage + "." + serializedType.getName();
    }

    @Override
    public SerializedType typeForClass(Class type) {
        if (type.getName().startsWith(basePackage)) {
            String shortName = type.getName().replace(basePackage + ".", "");
            return new SimpleSerializedType(shortName, getRevisionResolver().revisionOf(type));
        } else {
            return new SimpleSerializedType(type.getName(), getRevisionResolver().revisionOf(type));
        }
    }


}

