package br.com.locadora.luaazul.controllers;

import br.com.locadora.luaazul.model.Filme;
import br.com.locadora.luaazul.services.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService service;

    @GetMapping
    public List<Filme> findAll() {
        return service.findAll();
    }

}
