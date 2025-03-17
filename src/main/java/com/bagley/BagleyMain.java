package com.bagley;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BagleyMain {

    public static void main(String[] args) {
        SpringApplication.run(BagleyMain.class, args);
    }

    @Bean
    public CommandLineRunner iniciarBagley() {
        return args -> {
            System.out.println("🔹 Servidor Spring Boot iniciado!");
            System.out.println("🔹 Inicializando Bagley AI...");

            BagleyAI.carregarRespostas();
            System.out.println("🔹 Respostas carregadas para Bagley AI!");
        };
    }
}
