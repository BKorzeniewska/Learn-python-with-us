package com.example.learnpython.challenge;

import com.google.gson.Gson;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;


@Converter(autoApply = true)
@Component
public class JsonConverter implements AttributeConverter<ContentJson, String> {

    private final static Gson GSON = new Gson();

    @Override
    public String convertToDatabaseColumn(ContentJson mjo) {
        return GSON.toJson(mjo);
    }

    @Override
    public ContentJson convertToEntityAttribute(String dbData) {
        return GSON.fromJson(dbData, ContentJson.class);
    }
}