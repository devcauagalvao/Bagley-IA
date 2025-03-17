package com.bagley;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bagley/controller")
public class BagleyController {

    @GetMapping("/responder")
    public String responder(@RequestParam String pergunta) {
        return BagleyAI.obterResposta(pergunta);
    }

    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam String query) {
        return BagleyAI.pesquisarNaWeb(query);
    }
}
