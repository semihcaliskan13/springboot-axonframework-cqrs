package com.cqrsaxon.practice.productservice.config;

import org.axonframework.serialization.SerializedType;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.json.JacksonSerializer;

public class ShorterJacksonSerializer extends JacksonSerializer {

    protected ShorterJacksonSerializer(Builder builder) {
        super(builder);
    }

    protected String resolveClassName(SerializedType serializedType) {
        int lastIndexOfPackageName = serializedType.getName().lastIndexOf(".") + 1;
        String shortName = serializedType.getName().substring(lastIndexOfPackageName);
        return shortName;
    }


    @Override
    public SerializedType typeForClass(Class type) {
        int lastIndexOfPackageName = type.getName().lastIndexOf(".") + 1;
        String shortName = type.getName().substring(lastIndexOfPackageName);
        return new SimpleSerializedType(shortName, getRevisionResolver().revisionOf(type));
    }
}

