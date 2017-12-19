package br.com.locadora.luaazul.repositories;

import br.com.locadora.luaazul.model.Locacao;
import org.springframework.data.repository.CrudRepository;

public interface LocacaoRepository extends CrudRepository<Locacao, Long> {
}
