package com.bagley;

import com.bagley.utils.FileHandler;
import java.util.Map;
import java.util.Random;

public class ResponseGenerator {
    private final Map<String, String[]> responses;

    public ResponseGenerator() {
        this.responses = FileHandler.loadResponses("responses.json");
    }

    public String generateResponse(String intent, ContextManager context) {
        if (responses.containsKey(intent)) {
            String[] possibleResponses = responses.get(intent);
            return possibleResponses[new Random().nextInt(possibleResponses.length)];
        }
        return "Desculpe, não entendi. Pode reformular?";
    }
}
