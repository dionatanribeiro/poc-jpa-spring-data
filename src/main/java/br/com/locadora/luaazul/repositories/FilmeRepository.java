package br.com.locadora.luaazul.repositories;

import br.com.locadora.luaazul.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
