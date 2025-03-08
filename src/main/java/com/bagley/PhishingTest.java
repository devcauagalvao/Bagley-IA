package com.bagley;

import java.util.List;
import java.util.Arrays;

public class PhishingTest {

    private List<String> phishingTemplates = Arrays.asList(
            "Sua conta foi comprometida! Clique aqui para recuperar o acesso.",
            "Parabéns! Você ganhou um prêmio. Clique aqui para resgatar.",
            "Alerta de segurança! Sua conta foi usada em outro dispositivo. Faça login imediatamente para evitar bloqueio."
    );

    public void sendPhishingEmail(String email) {
        System.out.println("Bagley: Enviando e-mail de phishing para " + email);
        for (String template : phishingTemplates) {
            sendEmail(email, template);
        }
    }

    private void sendEmail(String email, String message) {
        // Aqui você pode integrar com um servidor SMTP para enviar e-mails reais (cuidado com a ética!).
        System.out.println("Bagley: Enviando mensagem: '" + message + "' para " + email);
        // Simulação de envio.
    }
}
