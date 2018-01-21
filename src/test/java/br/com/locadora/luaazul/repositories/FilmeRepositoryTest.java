package br.com.locadora.luaazul.repositories;

import br.com.locadora.luaazul.domain.Genero;
import br.com.locadora.luaazul.model.Filme;
import br.com.locadora.luaazul.model.Locacao;
import br.com.locadora.luaazul.model.Sinopse;
import br.com.locadora.luaazul.model.TipoPeriodo;
import br.com.locadora.luaazul.projections.FilmeOnlyNomeEDuracao;
import br.com.locadora.luaazul.projections.FilmeProjection;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public class FilmeRepositoryTest extends AbstractTest {

    @Autowired
    private FilmeRepository repository;

    @Test
    public void whenInsertFilme() {
        // Arrange
        Filme filme = Filme.builder()
                .nome("Inception")
                .capa("inception.jpg")
                .dtLancamento(LocalDate.now())
                .duracao(145)
                .genero(Genero.SCI_FI)
                .quantidade(5)
                .build();

        // Act
        repository.save(filme);

        // Assert
        Assert.assertNotNull("Id não deve ser nulo", filme.getId());
    }

    @Test
    public void quandoInsereFilmeComTabela1n1() {
        // Arrange
        Filme filme = Filme.builder()
                .nome("Inception")
                .capa("inception.jpg")
                .dtLancamento(LocalDate.now())
                .duracao(145)
                .genero(Genero.SCI_FI)
                .quantidade(5)
                .sinopse(Sinopse.builder().sinopseFilme("Info do filme").build())
                .build();

        // Act
        repository.save(filme);

        // Assert
        Assert.assertNotNull("Id não deve ser nulo", filme.getId());
        Assert.assertNotNull("Id da tabela filha não deve ser nulo", filme.getSinopse().getId());
        Assert.assertSame("FK da tabela filha é o mesmo da tabela pai", filme.getId(), filme.getSinopse().getFilme().getId());
    }

    @Test
    public void quandoBuscaFilmeComSinopse() {
        // Arrange
        Filme filme = Filme.builder()
                .nome("Inception")
                .capa("inception.jpg")
                .dtLancamento(LocalDate.now())
                .duracao(145)
                .genero(Genero.SCI_FI)
                .quantidade(5)
                .sinopse(Sinopse.builder().sinopseFilme("Info do filme").build())
                .build();
        repository.save(filme);

        // Act
        Optional<Filme> filmeOptional = repository.findById(1L);

        System.out.println(filmeOptional.get());
        System.out.println("#########################");
        System.out.println(filmeOptional.get().getSinopse());

        // Assert
//        Assert.assertSame("FK da tabela filha é o mesmo da tabela pai", filmeOptional.get().getId(), filmeOptional.get().getSinopse().getFilme().getId());
    }

    @Test
    public void whenFindFilmByName() {
        // Arrange
        String nomeDoFilme = "Interstellar";

        // Act
        Optional<Filme> interstellar = repository.findByNome(nomeDoFilme);

        // Assert
        Assert.assertTrue("Não deve ser nulo", interstellar.isPresent());
        System.out.println("###########################################");
        interstellar.get().getAtores().forEach(a -> System.out.println("\n" + a.getNome()));
    }

    @Test
    public void whenFindFilmByGenero() {
        // Arrange
        Genero sciFi = Genero.SCI_FI;

        // Act
        Optional<FilmeOnlyNomeEDuracao> filmeOnlyNomeEDuracao = repository.findByGenero(sciFi);

        // Assert
        Assert.assertTrue("Não deve ser nulo", filmeOnlyNomeEDuracao.isPresent());
        System.out.println("###########################################");
    }

    @Test
    public void whenUseProjection() {
        // Arrange
        Genero sciFi = Genero.SCI_FI;

        // Act
        Collection<FilmeProjection.Nome> todosFilmesComApenasNome = repository.findAllByGeneroOrderByNomeAsc(sciFi, FilmeProjection.Nome.class);
        Collection<FilmeProjection.NomeDuracaoGenero> todosFilmesComAlgumasColunas = repository.findAllByGeneroOrderByNomeAsc(sciFi, FilmeProjection.NomeDuracaoGenero.class);

        // Assert
        Assert.assertNotNull("Não deve ser vazio", todosFilmesComApenasNome);
        Assert.assertNotNull("Não deve ser vazio", todosFilmesComAlgumasColunas);
    }

    @Test
    public void quandoAdicionaLocacaoAoFilme() {
        // Arrange

        // cria filme
        Filme filme = Filme.builder()
                .nome("Inception")
                .capa("inception.jpg")
                .dtLancamento(LocalDate.now())
                .duracao(145)
                .genero(Genero.SCI_FI)
                .quantidade(5)
                .sinopse(Sinopse.builder().sinopseFilme("Info do filme").build())
                .build();
        repository.save(filme);

        // cria locacao
        Locacao locacao = new Locacao();
        locacao.setDtLimiteEntrega(LocalDate.now());
        TipoPeriodo tipoPeriodo = new TipoPeriodo();
        tipoPeriodo.setId(1L);
        locacao.setTipoPeriodo(tipoPeriodo);
        locacao.setValor(new BigDecimal("100"));
        filme.addLocacao(locacao);

        // Act
        repository.save(filme);

        // Assert
        Assert.assertNotNull(filme.getLocacoes().stream().findFirst().get().getId());
    }

}
