package br.com.locadora.luaazul.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {"filme"})
@ToString(exclude = {"filme"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sinopse {//extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Anotação necessária para mapear a coluna para o tipo CLOB, permitindo persistir
     * uma string com tamanho acima do padrão que é varchar(255)
     */
//    @Lob
    private String sinopseFilme;

    /**
     * Em uma operação de delete de Sinopse não é necessário informar o tipo de cascade
     * porque não é interessante por exemplo excluir o Filme junto com este registro.
     */
    @OneToOne(mappedBy = "sinopse", fetch = FetchType.EAGER)
    @JoinColumn(name = "filmeId")
    private Filme filme;

}
