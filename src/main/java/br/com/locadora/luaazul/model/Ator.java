package br.com.locadora.luaazul.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"filmes"})
@ToString(exclude = {"filme"})
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

    /**
     * Método conveniente para adicionar filme
     */
    public void addFilme(Filme filme) {
        if (this.filmes == null) {
            this.filmes = new HashSet<>();
        }
        this.filmes.add(filme);
        filme.addAtor(this);
    }

}
