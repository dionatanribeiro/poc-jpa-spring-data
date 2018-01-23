package br.com.locadora.luaazul.repositories;

import br.com.locadora.luaazul.model.Filme;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SinopseRepositoryTest extends AbstractTest {

    @Autowired
    private SinopseRepository repository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Test
    public void quandoDeletaSinopse() {
        // Arrange
        System.out.println("Remove vínculo de sinopse com filme");
        Filme filme = filmeRepository.findById(1L).get();
        filme.setSinopse(null);
        filmeRepository.save(filme);
        System.out.println("Vínculo de sinopse com filme removido");

        long countAntes = repository.count();
        Long theId = 1L;

        // Act

        repository.deleteById(theId);
        long countDepois = repository.count();

        // Assert
        Assert.assertThat(countDepois, Matchers.lessThan(countAntes));
    }

}
