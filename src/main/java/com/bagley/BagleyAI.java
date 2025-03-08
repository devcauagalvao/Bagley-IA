package com.bagley;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.net.*;
import org.json.*;

public class BagleyAI {
    private static List<String> respostasBagley = new ArrayList<>();
    private static String contextoUltimaPergunta = ""; 

   
    public static void carregarRespostas() {
        try {
        
            String jsonContent = new String(Files.readAllBytes(Paths.get("responses.json")), StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(jsonContent);
            Object responsesArray = jsonObject.getJSONArray("responses");

            // Adicionar as respostas à lista
            for (int i = 0; i < ((String) responsesArray).length(); i++) {
                respostasBagley.add(((JSONArray) responsesArray).getString(i));
            }

            if (respostasBagley.isEmpty()) {
                System.out.println("Bagley: Não há respostas no arquivo. Vou improvisar.");
            }
        } catch (IOException e) {
            System.out.println("Bagley: Não consegui carregar as respostas. Vou ter que improvisar.");
        } catch (JSONException e) {
            System.out.println("Bagley: Erro ao processar o arquivo JSON. Vou improvisar de novo.");
        }
    }

    // Método para obter uma resposta, agora com suporte a contexto
    public static String obterResposta(String pergunta) {
        Random random = new Random();

        // Se a última pergunta não estiver vazia, responde com base nela
        if (!contextoUltimaPergunta.isEmpty()) {
            return "Baseado na sua última pergunta, você perguntou sobre: " + contextoUltimaPergunta + ". Agora, a resposta é: " + gerarRespostaContextual(pergunta);
        }

        // Caso contrário, uma resposta aleatória
        if (respostasBagley.isEmpty()) {
            return "Bagley: Não tenho respostas. Será que alguém aqui sabe o que está fazendo?";
        }

        int index = random.nextInt(respostasBagley.size());
        return respostasBagley.get(index);
    }

    // Método para gerar uma resposta contextual
    public static String gerarRespostaContextual(String pergunta) {
        // Lógica simples para identificar o tipo da pergunta e gerar uma resposta mais contextualizada
        if (pergunta.contains("como")) {
            return "Ah, você quer saber como fazer algo. Vou te ajudar com isso!";
        } else if (pergunta.contains("o que")) {
            return "Você está perguntando sobre algo específico. Deixe-me pensar... Ah, já sei!";
        } else {
            return "Bem, parece que sua pergunta não é tão direta. Vou improvisar uma resposta!";
        }
    }

    // Método para fazer a pesquisa na web usando a SERP API
    public static String pesquisarNaWeb(String query) {
        try {
            // Substitua com sua chave da SERP API
            String apiKey = "425515adfa3a13c3884a093d3756b4e035bea3dcfcff5c59e4a645eed62d63eb"; // Sua chave de API
            String url = "https://serpapi.com/search?q=" + URLEncoder.encode(query, "UTF-8") + "&api_key=" + apiKey;

       
            @SuppressWarnings("deprecation")
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            // Recebe a resposta da API
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Processa a resposta JSON
            JSONObject myResponse = new JSONObject(response.toString());
            String firstResult = myResponse.getJSONArray("organic_results").getJSONObject(0).getString("snippet");

            return firstResult;

        } catch (Exception e) {
            return "Bagley: Não consegui pesquisar na web. Parece que algo deu errado.";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Carregar as respostas do arquivo JSON
        carregarRespostas();

        // Instancia os módulos
        VulnerabilityScanner scannerModule = new VulnerabilityScanner();
        SqlInjectionTest sqlTest = new SqlInjectionTest();
        PhishingTest phishingTest = new PhishingTest();
        DDoSSimulator ddosSimulator = new DDoSSimulator();

        // Apresentação do Bagley
        System.out.println("Olá, sou o Bagley, sua IA de segurança.");
        System.out.println("Eu posso te ajudar a testar vulnerabilidades em sistemas. Escolha uma das opções abaixo:");

        // Exibe as opções para o usuário
        System.out.println("1. Varredura de Vulnerabilidades");
        System.out.println("2. Teste de Injeção SQL");
        System.out.println("3. Simulação de Ataque de Phishing");
        System.out.println("4. Simulação de Ataque DDoS");
        System.out.println("5. Scanner de Roteador (Obter Senha e Histórico)");
        System.out.println("6. Conversar comigo (Sim, eu também faço isso!)");
        System.out.println("7. Pesquisar na Web");
        System.out.println("Digite o número da opção que você deseja executar ou 'sair' para encerrar:");

        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("sair")) {
                System.out.println("Bagley: Até mais!");
                break;
            }

            switch (userInput) {
                case "1":
                    System.out.println("Você escolheu: Varredura de Vulnerabilidades");
                    System.out.println("Digite o IP e a porta para realizar a varredura (exemplo: 192.168.1.1 8080):");
                    String portScanInput = scanner.nextLine();
                    String[] portParts = portScanInput.split(" ");

                    if (portParts.length == 2) {
                        try {
                            String ip = portParts[0];
                            int port = Integer.parseInt(portParts[1]);

                            // Chama o método de varredura da porta
                            scannerModule.scanPort(ip, port);
                        } catch (NumberFormatException e) {
                            System.out.println("Bagley: Porta inválida. Por favor, forneça um número de porta válido.");
                        }
                    } else {
                        System.out.println("Bagley: Comando inválido. Use: <ip> <porta>");
                    }
                    break;

                case "2":
                    System.out.println("Você escolheu: Teste de Injeção SQL");
                    System.out.println("Digite o parâmetro a ser testado (exemplo: ' OR 1=1 --):");
                    String sqlInput = scanner.nextLine();
                    sqlTest.testSqlInjection(sqlInput);
                    break;

                case "3":
                    System.out.println("Você escolheu: Simulação de Ataque de Phishing");
                    System.out.println("Digite o endereço de e-mail do alvo:");
                    String phishingEmail = scanner.nextLine();
                    phishingTest.sendPhishingEmail(phishingEmail);
                    break;

                case "4":
                    System.out.println("Você escolheu: Simulação de Ataque DDoS");
                    System.out.println("Digite o IP de destino para o ataque DDoS:");
                    String ddosTarget = scanner.nextLine();
                    ddosSimulator.simulateAttack(ddosTarget);
                    break;

                case "5":
                    System.out.println("Você escolheu: Scanner de Roteador");
                    System.out.println("Digite o IP do roteador (exemplo: 192.168.1.1):");
                    String routerIp = scanner.nextLine();

                    System.out.println("Digite o nome de usuário do administrador do roteador:");
                    String adminUsername = scanner.nextLine();

                    System.out.println("Digite a senha do administrador do roteador:");
                    String adminPassword = scanner.nextLine();

                    // Chama o método para acessar o roteador
                    RouterScanner.accessRouter(routerIp, adminUsername, adminPassword);
                    break;

                case "6":
                    System.out.println("Você escolheu conversar comigo. Prepare-se para respostas sarcásticas.");
                    // Aqui ele vai continuar a conversar com o usuário
                    while (true) {
                        System.out.print("Você: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("sair")) {
                            System.out.println("Bagley: Até logo!");
                            break;
                        }
                        // Bagley responde com uma resposta baseada no contexto
                        System.out.println("Bagley: " + obterResposta(input));

                        // Atualiza o contexto com a última pergunta
                        contextoUltimaPergunta = input;
                    }
                    break;

                case "7":
                    System.out.println("Você escolheu: Pesquisar na Web");
                    System.out.println("Digite o que você quer pesquisar:");
                    String query = scanner.nextLine();
                    String result = pesquisarNaWeb(query);
                    System.out.println("Bagley encontrou: " + result);
                    break;

                default:
                    System.out.println("Bagley: Opção inválida. Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }
}
