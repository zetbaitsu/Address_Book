package com.zelory.kace.adressbook.util;

import com.google.gson.Gson;

public class JsonParser {
    private static JsonParser INSTANCE;

    private Gson parser;

    public static JsonParser getInstance() {
        if (INSTANCE == null) {
            synchronized (JsonParser.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JsonParser();
                }
            }
        }
        return INSTANCE;
    }

    private JsonParser() {
        parser = new Gson();
    }

    public Gson getParser() {
        return parser;
    }
}
