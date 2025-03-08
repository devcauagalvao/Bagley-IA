package com.bagley;

import java.util.HashMap;
import java.util.Map;

public class ContextManager {
    private final Map<String, String> contextData = new HashMap<>();

    public void updateContext(String key, String value) {
        contextData.put(key, value);
    }

    public String getContext(String key) {
        return contextData.getOrDefault(key, "");
    }
}
