package br.com.locadora.luaazul.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sinopse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Anotação necessária para mapear a coluna para o tipo CLOB, permitindo persistir
     * uma string com tamanho acima do padrão que é varchar(255)
     */
    @Lob
    private String sinopseFilme;

    /**
     * Em uma operação de delete de Sinopse não é necessário informar o tipo de cascade
     * porque não é interessante por exemplo excluir o Filme junto com este registro.
     */
    @OneToOne
    private Filme filme;

}
