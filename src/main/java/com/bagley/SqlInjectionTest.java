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
          
            System.out.println("Bagley: Testando com payload: " + payload);
         
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
