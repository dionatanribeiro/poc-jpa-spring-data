package br.com.locadora.luaazul.repositories;

import br.com.locadora.luaazul.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
