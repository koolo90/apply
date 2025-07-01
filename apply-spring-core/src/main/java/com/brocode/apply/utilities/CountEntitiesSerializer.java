package com.brocode.apply.utilities;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.util.Pair;

import java.io.IOException;

@SuppressWarnings("squid:S3740")
public class CountEntitiesSerializer extends JsonSerializer<Pair> {
    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param value       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     *                    serializing Objects value contains, if any.
     */
    @Override
    public void serialize(Pair value,
                          JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("count", value.getFirst());
        gen.writeObjectField("entity", value.getSecond());
        gen.writeEndObject();
    }
}
