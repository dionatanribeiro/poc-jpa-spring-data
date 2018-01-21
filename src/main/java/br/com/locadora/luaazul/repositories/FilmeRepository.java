package br.com.locadora.luaazul.repositories;

import br.com.locadora.luaazul.domain.Genero;
import br.com.locadora.luaazul.model.Filme;
import br.com.locadora.luaazul.projections.FilmeOnlyNomeEDuracao;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

    Optional<Filme> findByNome(String nome);

    // projection de interface
    Optional<FilmeOnlyNomeEDuracao> findByGenero(Genero genero);

    // projection dinamica
    <T> Collection<T> findAllByGeneroOrderByNomeAsc(Genero genero, Class<T> type);

    @Value
    class FilmeNome {
        String nome;
    }

    @Value
    class FilmeNomeDuracaoGenero {
        String nome;
        Integer duracao;
        Genero genero;
    }

}
