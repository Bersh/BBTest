package com.example.bbtest;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class LatLngDeserializer implements JsonDeserializer<LatLng> {

    @Override
    public LatLng deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jobject = json.getAsJsonObject();

        return new LatLng(
                jobject.get("lat").getAsDouble(),
                jobject.get("lon").getAsDouble());
    }
}