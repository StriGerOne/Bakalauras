package com.example.reservation.Serializer;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@RequiresApi(api = Build.VERSION_CODES.O)
public class DataTimeSerializer implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        String ldtString = jsonElement.getAsString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalTime.parse(ldtString, DateTimeFormatter.ISO_TIME);
        }
        return null;
    }

    @Override
    public JsonElement serialize(LocalTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_TIME));
        }
        return null;
    }
}