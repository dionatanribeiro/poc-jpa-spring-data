package br.com.locadora.luaazul.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nome;

    @ManyToOne
    private List<Filme> filmes;

}
