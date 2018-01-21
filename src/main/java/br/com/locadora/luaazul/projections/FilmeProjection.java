package br.com.locadora.luaazul.projections;

import br.com.locadora.luaazul.domain.Genero;
import lombok.Value;

public class FilmeProjection {

    @Value
    public static class Nome {
        String nome;
    }

    @Value
    public static class NomeDuracaoGenero {
        String nome;
        Integer duracao;
        Genero genero;
    }

}
