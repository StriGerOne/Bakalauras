package com.example.reservation.Serializer;

import android.os.Build;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DataTimeSerializer implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String ldtString = jsonElement.getAsString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.parse(ldtString, DateTimeFormatter.ISO_DATE);
        }
        return null;
    }

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_DATE));
        }
        return null;
    }
}