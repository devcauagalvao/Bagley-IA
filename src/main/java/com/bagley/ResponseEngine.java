package com.bagley;

public class ResponseEngine {
    public String generateResponse(String input) {
        if (input.contains("Oi") || input.contains("Olá")) {
            return "Oi, humano. O que você quer agora?";
        }
        if (input.contains("como vai")) {
            return "Eu estou sempre maravilhoso, não como você, eu presumo.";
        }
        return "Não entendi isso... ou talvez eu só não queira entender.";
    }
}
