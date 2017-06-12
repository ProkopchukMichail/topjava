package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by comp on 10.06.2017.
 */
public class LocalDateTimeConverter {
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime>{
        @Override
        public void serialize(LocalDateTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
            arg1.writeString(arg0.toString());
        }

        @Override
        public Class<LocalDateTime> handledType() {
            return LocalDateTime.class;
        }
    }

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
            return LocalDateTime.parse(arg0.getText());
        }

        @Override
        public Class<?> handledType() {
            return LocalDateTime.class;
        }
    }
}
