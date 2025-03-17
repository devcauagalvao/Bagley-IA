package com.bagley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;

@SpringBootApplication
@RestController
@RequestMapping("/api/bagley")
public class BagleyAI {

    private static List<String> respostasBagley = new ArrayList<>();
    private static String contextoUltimaPergunta = "";

    public static void main(String[] args) {
        SpringApplication.run(BagleyAI.class, args);
        carregarRespostas();
        System.out.println("🔹 Servidor Bagley iniciado!");
    }

    @GetMapping("/responder")
    public String responder(@RequestParam String pergunta) {
        return obterResposta(pergunta);
    }

    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam String query) {
        return pesquisarNaWeb(query);
    }




    static void carregarRespostas() {
        try {
            
            InputStream inputStream = BagleyAI.class.getClassLoader().getResourceAsStream("responses.json");
    
            if (inputStream == null) {
                System.out.println("Bagley: Arquivo responses.json não encontrado.");
                return;
            }
    
       
            String jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(jsonContent);
    
    
            if (!jsonObject.has("status")) {
                System.out.println("Bagley: Chave 'status' não encontrada no arquivo JSON.");
                return;
            }
            
     
            JSONArray statusArray = jsonObject.getJSONArray("status");
            JSONArray identidadeArray = jsonObject.getJSONArray("identidade");
            JSONArray segurancaArray = jsonObject.getJSONArray("seguranca");
            JSONArray eticoArray = jsonObject.getJSONArray("etico");
    
            for (int i = 0; i < statusArray.length(); i++) {
                respostasBagley.add(statusArray.getString(i));
            }
            for (int i = 0; i < identidadeArray.length(); i++) {
                respostasBagley.add(identidadeArray.getString(i));
            }
            for (int i = 0; i < segurancaArray.length(); i++) {
                respostasBagley.add(segurancaArray.getString(i));
            }
            for (int i = 0; i < eticoArray.length(); i++) {
                respostasBagley.add(eticoArray.getString(i));
            }
    
            if (respostasBagley.isEmpty()) {
                System.out.println("Bagley: Nenhuma resposta encontrada.");
            }
    
        } catch (IOException | JSONException e) {
            System.out.println("Bagley: Erro ao carregar respostas.");
            e.printStackTrace();
        }
    }
    
    
    








    static String obterResposta(String pergunta) {
        Random random = new Random();

        if (!contextoUltimaPergunta.isEmpty()) {
            return "Baseado na sua última pergunta (" + contextoUltimaPergunta + "), aqui está minha resposta: " + gerarRespostaContextual(pergunta);
        }

        if (respostasBagley.isEmpty()) {
            return "Bagley: Não tenho respostas. Mas sou ótimo em improvisar!";
        }

        int index = random.nextInt(respostasBagley.size());
        return respostasBagley.get(index);
    }

    private static String gerarRespostaContextual(String pergunta) {
        if (pergunta.toLowerCase().contains("como")) {
            return "Ah, você quer saber como fazer algo. Vou te ajudar com isso!";
        } else if (pergunta.toLowerCase().contains("o que")) {
            return "Você está perguntando sobre algo específico. Deixe-me pensar... Já sei!";
        } else {
            return "Hmm... essa pergunta é curiosa. Vou improvisar!";
        }
    }

    static String pesquisarNaWeb(String query) {
        try {
            String apiKey = "425515adfa3a13c3884a093d3756b4e035bea3dcfcff5c59e4a645eed62d63eb";
            String url = "https://serpapi.com/search?q=" + URLEncoder.encode(query, "UTF-8") + "&api_key=" + apiKey;

            @SuppressWarnings("deprecation")
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject myResponse = new JSONObject(response.toString());
            return myResponse.getJSONArray("organic_results").getJSONObject(0).getString("snippet");

        } catch (Exception e) {
            return "Bagley: Não consegui pesquisar na web. Algo deu errado.";
        }
    }
}
