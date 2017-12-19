package br.com.locadora.luaazul.services;

import br.com.locadora.luaazul.model.Filme;
import br.com.locadora.luaazul.repositories.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    public List<Filme> findAll() {
        return repository.findAll();
    }

}
