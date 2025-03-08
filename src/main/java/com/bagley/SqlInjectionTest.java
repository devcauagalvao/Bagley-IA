package com.bagley;

import java.util.Arrays;
import java.util.List;

public class SqlInjectionTest {

    private List<String> commonSqlPayloads = Arrays.asList(
            "' OR '1'='1'; --", 
            "' DROP TABLE users; --", 
            "' UNION SELECT username, password FROM users; --"
    );

    public void testSqlInjection(String url) {
        System.out.println("Bagley: Iniciando teste de SQL Injection em " + url);
        for (String payload : commonSqlPayloads) {
            String testUrl = url + "?input=" + payload;
            // Simula um envio HTTP (não está implementado de forma real aqui)
            System.out.println("Bagley: Testando com payload: " + payload);
            // Aqui você poderia fazer uma requisição real HTTP e verificar a resposta.
            // Exemplo fictício de resposta:
            boolean vulnerable = mockHttpRequest(testUrl);
            if (vulnerable) {
                System.out.println("Bagley: Vulnerabilidade encontrada com o payload: " + payload);
            } else {
                System.out.println("Bagley: Nenhuma vulnerabilidade encontrada para o payload: " + payload);
            }
        }
    }

    private boolean mockHttpRequest(String url) {
        // Função fictícia para simulação
        return url.contains("' OR '1'='1'; --");
    }
}
