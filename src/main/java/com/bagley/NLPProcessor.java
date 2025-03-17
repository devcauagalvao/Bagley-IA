package com.bagley;

import com.bagley.utils.PatternRecognizer;
import java.util.logging.Logger;

public class NLPProcessor {

    private static final Logger logger = Logger.getLogger(NLPProcessor.class.getName());

    public String analyze(String input) {
        
        if (input == null || input.trim().isEmpty()) {
            logger.warning("Entrada vazia ou nula detectada!");
            return "Desculpe, não entendi. Poderia reformular?";
        }

    
        logger.info("Entrada do usuário: " + input);

        try {
    
            String pattern = PatternRecognizer.identifyPattern(input);
            logger.info("Padrão identificado: " + pattern);
            return pattern;
        } catch (Exception e) {
         
            logger.severe("Erro ao identificar o padrão: " + e.getMessage());
            return "Houve um erro ao processar sua solicitação.";
        }
    }
}
