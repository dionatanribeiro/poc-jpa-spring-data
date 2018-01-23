package br.com.locadora.luaazul.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@EqualsAndHashCode(exclude = {"filmes"})
@ToString(exclude = {"filmes"})
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
    @ManyToMany(mappedBy = "atores", fetch = FetchType.LAZY)
    private Set<Filme> filmes;

}
