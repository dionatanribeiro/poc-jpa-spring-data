package br.com.locadora.luaazul.model;

import br.com.locadora.luaazul.domain.Genero;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"locacoes", "atores", "sinopse"})
@ToString(exclude = {"locacoes", "atores", "sinopse"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filme {//extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer duracao;

    private LocalDate dtLancamento;

    @Enumerated(value = EnumType.STRING)
    private Genero genero;

    private Integer quantidade;

//    @Lob
//    private Byte[] capa;
    private String capa;

    /**
     * Relação de 1 para 1, onde operações para esta entidade ocorrerão em cascata junto
     * com a entidade relacionada, por exemplo ao excluir a entidade Filme, a entidade
     * Sinopse será excluída junta.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sinopseId")
    private Sinopse sinopse;

    /**
     * Um filme pode ter um grande histórico de locacoes, então neste é informado
     * uma lista aqui.
     * Não é interessante manipular registros repetidos, portando neste caso é usado Set
     * Ao excluir um filme é importante excluir juntamente seu histórico
     * Dentro da entidade locacao, o filme deverá ser mapeado para o campo chamado "filme"
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "filme", fetch = FetchType.LAZY)
    private Set<Locacao> locacoes;

    /**
     * Em uma relação de muitos para muitos é possível criar uma tabela dinamicamente
     * utilizando `@JoinTable`, onde é informado o nome da tabela, a "joinColumns" onde é
     * informado o id da entidade local, juntamente com o nome e a "inverseJoinColumns" onde
     * é mapeado pelo id da segunda entidade
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinTable(
        name = "filme_ator",
        joinColumns = @JoinColumn(name = "filme_id"),
        inverseJoinColumns = @JoinColumn(name = "ator_id")
    )
    private Set<Ator> atores;

    /**
     * Método conveniente para adicionar locação
     */
    public void addLocacao(Locacao novaLocacao) {

        // verifica se a lista ainda não foi instanciada
        if (this.locacoes == null) {
            this.locacoes = new HashSet<>();
        }

        // adiciona item a lista
        this.locacoes.add(novaLocacao);

        // relaciona parent
        novaLocacao.setFilme(this);
    }

    /**
     * Método conveniente para adicionar ator
     */
    public void addAtor(Ator novoAtor) {

        // verifica se a lista ainda não foi instanciada
        if (this.atores == null) {
            this.atores = new HashSet<>();
        }

        // adiciona item a lista
        this.atores.add(novoAtor);

        // relaciona parent
//        novoAtor.addFilme(this);
    }

}
