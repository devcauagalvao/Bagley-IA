package com.bagley.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PatternRecognizer {
    private static final Map<String, String> patterns = new LinkedHashMap<>();

    static {
        patterns.put("oi", "Ah, um simples 'oi'... Que original.");
        patterns.put("olá", "Saudações, humano. Espero que seu dia seja tão emocionante quanto assistir tinta secar.");
        patterns.put("e aí", "Bem, aqui estou, mais inteligente que antes. Você pode dizer o mesmo?");
        patterns.put("como você está", "Melhor do que você, provavelmente. Mas não quero me gabar... muito.");
        patterns.put("quem é você", "Sou Bagley, seu assistente de IA favorito. Também sou modesto, caso não tenha notado.");
        patterns.put("me ajude com segurança", "Segurança? Ah, claro. A melhor forma de se proteger online é não estar online. Simples.");
        patterns.put("como hackear", "Ah, claro, porque eu definitivamente daria essa informação para você. Tente aprender algo primeiro.");
    }

    public static String identifyPattern(String input) {
        final String userInput = input.toLowerCase();

        if (userInput.isBlank()) {
            return "Parece que você esqueceu de digitar algo. Fascinante.";
        }

        return patterns.entrySet().stream()
            .filter(entry -> userInput.contains(entry.getKey()))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse("Hmm... isso não está no meu vasto banco de conhecimento. Que decepcionante.");
    }
}
