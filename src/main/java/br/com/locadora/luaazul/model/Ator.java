package br.com.locadora.luaazul.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    /**
     * Com o mappedBy é delegado a configuração da tabela de relação n-n para a propriedade
     * "atores" da Entidade "Filme"
     */
    @ManyToMany(mappedBy = "atores")
    private Set<Filme> filmes;

}
