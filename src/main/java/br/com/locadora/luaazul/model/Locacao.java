package br.com.locadora.luaazul.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@EqualsAndHashCode(exclude = {"filme", "tipoPeriodo"})
@ToString(exclude = {"filme", "tipoPeriodo"})
@AllArgsConstructor
@NoArgsConstructor
public class Locacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Uma locação é referente a apenas um filme.
     * Não queremos uma operação de delete em cascata removendo o Filme, apenas a locação.
     * Portanto a operação em cascata não é informada.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Filme filme;

    // todo
//    @ManyToMany
//    private Cliente cliente;

    private LocalDate dtLimiteEntrega;

    private BigDecimal valor;

    /**
     * O "FetchType.EAGER" é informado porque desejo que o TipoPeriodo sempre seja
     * carregado juntamente com a Locacao
     */
    @OneToOne(fetch = FetchType.EAGER)
    private TipoPeriodo tipoPeriodo;

}
