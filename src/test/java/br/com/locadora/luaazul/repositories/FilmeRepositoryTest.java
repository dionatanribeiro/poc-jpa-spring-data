package br.com.locadora.luaazul.repositories;

import br.com.locadora.luaazul.domain.Genero;
import br.com.locadora.luaazul.model.*;
import br.com.locadora.luaazul.projections.FilmeOnlyNomeEDuracao;
import br.com.locadora.luaazul.projections.FilmeProjection;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
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

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private AtorRepository atorRepository;

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
    }

    @Test
    public void quandoBuscaFilmeComSinopse() {
        // Arrange
        Long theId = 1L;

        // Act
        Optional<Filme> filmeOptional = repository.findById(theId);

        // Assert
        Assert.assertSame("FK da tabela filha é o mesmo da tabela pai", filmeOptional.get().getId(), filmeOptional.get().getSinopse().getFilme().getId());
    }

    @Test
    public void quandoDeletaFilme() {
        // Arrange
        Long theId = 1L;

        Filme filme = repository.findById(theId).get();
        filme.setSinopse(null);
        repository.save(filme);

        long countAntes = repository.count();

        // Act
        repository.deleteById(theId);
        long countDepois = repository.count();

        // Assert
        Assert.assertThat(countDepois, Matchers.lessThan(countAntes));
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
        Long theId = 1L;
        Filme filme = repository.findById(theId).get();

        TipoPeriodo tipoPeriodo = new TipoPeriodo();
        tipoPeriodo.setId(1L);

        Locacao locacao = Locacao.builder()
                .dtLimiteEntrega(LocalDate.now())
                .tipoPeriodo(tipoPeriodo)
                .valor(new BigDecimal("100"))
                .build();
        filme.addLocacao(locacao);

        Locacao locacao2 = Locacao.builder()
                .dtLimiteEntrega(LocalDate.now())
                .tipoPeriodo(tipoPeriodo)
                .valor(new BigDecimal("125"))
                .build();
        filme.addLocacao(locacao2);

        // Act
        repository.save(filme);
        System.out.println(filme.getLocacoes());

        // Assert
        Assert.assertNotNull(filme.getLocacoes().stream().findFirst().get().getId());
        Assert.assertEquals(2, locacaoRepository.count());
    }

    @Test
    public void quandoVinculaAtoresAoFilme() {
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
        System.out.println("Filme salvo: " + filme);

        Ator ator = Ator.builder()
                .nome("Leonador DiCaprio")
                .build();
        atorRepository.save(ator);
        System.out.println("Ator salvo: " + ator);

        Ator ator2 = Ator.builder()
                .nome("Ellen Page")
                .build();
        atorRepository.save(ator2);
        System.out.println("Ator salvo: " + ator2);

        System.out.println("Vinculando atores ao filme...");

        filme.addAtor(ator);
        filme.addAtor(ator2);
        // Aqui é utilizado o "flush" para realizar um commit em alteações pendentes
        // nas entidades, ao fazer isso ele vai inserir o vinculo dos atores na tabela de
        // N-N, é importante notar que o repositório utilizado para o flush é o da entidade
        // "Filme", as alterações pendentes nesta transação serão feitas apenas para esta tabela.
        repository.flush();

        System.out.println("Testa remoção de um ator do filme e commita entidade Filme");
        filme.getAtores().remove(ator2);
        repository.flush();

        System.out.println("Deleta o ultimo filme adicionado");
        repository.delete(filme);
        repository.flush();
    }

}
