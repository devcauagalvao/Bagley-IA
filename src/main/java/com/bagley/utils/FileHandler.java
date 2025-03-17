package com.bagley.utils;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandler {
    @SuppressWarnings("unchecked")
    public static Map<String, String[]> loadResponses(String fileName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File("src/resources/" + fileName), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

