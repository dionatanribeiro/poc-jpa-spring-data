package br.com.locadora.luaazul.model;

import br.com.locadora.luaazul.domain.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer duracao;

    private LocalDate dtLancamento;

    @Enumerated(value = EnumType.STRING)
    private Genero genero;

    private Integer quantidade;

    @Lob
//    private Byte[] capa;
    private String capa;

    /**
     * Relação de 1 para 1, onde operações para esta entidade ocorrerão em cascata junto
     * com a entidade relacionada, por exemplo ao excluir a entidade Filme, a entidade
     * Sinopse será excluída junta.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Sinopse sinopse;

    /**
     * Um filme pode ter um grande histórico de locacoes, então neste é informado
     * uma lista aqui.
     * Não é interessante manipular registros repetidos, portando neste caso é usado Set
     * Ao excluir um filme é importante excluir juntamente seu histórico
     * Dentro da entidade locacao, o filme deverá ser mapeado para o campo chamado "filme"
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "filme")
    private Set<Locacao> locacoes;

    @ManyToMany
    @JoinTable(
        name = "filme_ator",
        joinColumns = @JoinColumn(name = "filme_id"),
        inverseJoinColumns = @JoinColumn(name = "ator_id")
    )
    private Set<Ator> atores;

}
