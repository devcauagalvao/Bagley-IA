package com.bagley;

import com.bagley.utils.PatternRecognizer;

public class NLPProcessor {
    public String analyze(String input) {
        System.out.println("Entrada do usuário: " + input); // Debug
        String pattern = PatternRecognizer.identifyPattern(input);
        System.out.println("Padrão identificado: " + pattern); // Debug
        return pattern;
    }
}
