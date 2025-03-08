package com.bagley;

import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.Scanner;

public class RouterScanner {

    // Realiza o login no roteador
    public static void accessRouter(String routerIp, String adminUsername, String adminPassword) {
        try {
            // Tenta realizar a requisição de login no roteador
            @SuppressWarnings("deprecation")
            URL url = new URL("http://" + routerIp + "/login"); // URL de login do roteador
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String urlParameters = "username=" + adminUsername + "&password=" + adminPassword;

            // Enviar as credenciais no corpo da requisição
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = urlParameters.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Ler a resposta após a tentativa de login
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();

            System.out.println("Resposta do roteador após login: " + content.toString());

            // Agora que você tem acesso, vamos tentar obter a senha e o histórico de navegação
            getRouterHistory(routerIp, adminUsername, adminPassword);
            getRouterPassword(routerIp, adminUsername, adminPassword);

        } catch (IOException e) {
            System.out.println("Erro ao tentar acessar o roteador: " + e.getMessage());
        }
    }

    // Obtém o histórico de navegação do roteador
    @SuppressWarnings("deprecation")
    public static void getRouterHistory(String routerIp, String adminUsername, String adminPassword) {
        try {
            // Requisição para pegar o histórico de navegação (a URL pode variar de acordo com o modelo do roteador)
            URL url = new URL("http://" + routerIp + "/logs"); // URL dos logs de histórico de navegação
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Autenticação básica
            String encodedCredentials = Base64.getEncoder().encodeToString((adminUsername + ":" + adminPassword).getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);

            // Ler os logs do roteador
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();

            System.out.println("Histórico de navegação do roteador: " + content.toString());

        } catch (IOException e) {
            System.out.println("Erro ao obter histórico de navegação: " + e.getMessage());
        }
    }

    // Obtém a senha configurada no roteador
    @SuppressWarnings("deprecation")
    public static void getRouterPassword(String routerIp, String adminUsername, String adminPassword) {
        try {
            // A URL para acessar configurações de senha do roteador (depende do modelo)
            URL url = new URL("http://" + routerIp + "/settings");  // Exemplo: URL onde a senha pode estar configurada
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Autenticação básica
            String encodedCredentials = Base64.getEncoder().encodeToString((adminUsername + ":" + adminPassword).getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);

            // Ler a resposta com as configurações de senha
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();

            // Aqui você pode processar o conteúdo e buscar a senha de rede ou de administração
            System.out.println("Configurações do roteador: " + content.toString());

        } catch (IOException e) {
            System.out.println("Erro ao obter a senha do roteador: " + e.getMessage());
        }
    }

    // Método principal para testar
    public static void main(String[] args) {
        // Solicita o IP do roteador, usuário e senha para o login
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o IP do roteador (exemplo: 192.168.1.1):");
        String routerIp = scanner.nextLine();

        System.out.println("Digite o nome de usuário do administrador do roteador:");
        String adminUsername = scanner.nextLine();

        System.out.println("Digite a senha do administrador do roteador:");
        String adminPassword = scanner.nextLine();

        // Acessar o roteador
        accessRouter(routerIp, adminUsername, adminPassword);

        scanner.close();
    }
}
