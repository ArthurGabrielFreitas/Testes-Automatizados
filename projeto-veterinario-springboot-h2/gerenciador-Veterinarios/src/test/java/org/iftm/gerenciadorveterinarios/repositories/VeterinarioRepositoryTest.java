package org.iftm.gerenciadorveterinarios.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VeterinarioRepositoryTest {
    @Autowired
    private VeterinarioRepository repositorio;

    @Test
    void testeBuscarPorNomeIgnorandoCase() {
        String nomeBusca = "ANA LUIZA BORGES";
        String nomeEsperado = "Ana Luiza Borges";

        List<Veterinario> resultado = repositorio.findByNomeIgnoreCase(nomeBusca);

        assertFalse(resultado.isEmpty(), "A lista não deveria estar vazia");
        assertEquals(nomeEsperado, resultado.get(0).getNome());
    }

    @Test
    void testeBuscarPorNomeInexistente() {
        String nomeBusca = "Zé Ninguém";

        List<Veterinario> resultado = repositorio.findByNomeIgnoreCase(nomeBusca);

        assertTrue(resultado.isEmpty(), "A lista deveria estar vazia");
    }

    @Test
    void testeBuscarPorPedacoDoNome() {
        String silabaBusca = "za";
        String nomeEsperado1 = "Ana Luiza Borges";
        String nomeEsperado2 = "Marcos Vinicius Souza";
        List<String> listaDeNomesEsperados = new ArrayList<>(List.of(nomeEsperado1, nomeEsperado2));

        List<Veterinario> resultado = repositorio.findByNomeContains(silabaBusca);
        List<String> listaDeNomesRetornados = resultado.stream().map(Veterinario::getNome).toList();

        assertEquals(2, resultado.size(), "Deveria retornar exatamente 2 veterinários");
        assertTrue(listaDeNomesRetornados.containsAll(listaDeNomesEsperados),
                "Os nomes retornados não correspondem aos esperados");
    }

    @Test
    void testeBuscarComStringVaziaRetornaTodos() {
        String silabaBusca = "";
        Long totalEsperado = repositorio.count();

        List<Veterinario> resultado = repositorio.findByNomeContains(silabaBusca);

        assertEquals(totalEsperado, resultado.size(), "Deveria retornar todos os veterinários cadastrados");
    }

    @Test
    void testeBuscarPorSalarioSuperior() {
        BigDecimal salarioMinimo = BigDecimal.valueOf(4000.0);

        List<Veterinario> resultado = repositorio.findBySalarioGreaterThan(salarioMinimo);

        assertFalse(resultado.isEmpty(), "Deveria retornar mais de um veterinário");
        assertTrue(resultado.stream().allMatch(v -> v.getSalario().compareTo(salarioMinimo) > 0),
                "Todo veterinário retornado deve ter salário maior que " + salarioMinimo);
    }

    @Test
    void testeBuscarPorSalarioInferior() {
        BigDecimal salarioMaximo = BigDecimal.valueOf(5000.0);

        List<Veterinario> resultado = repositorio.findBySalarioLessThan(salarioMaximo);

        assertFalse(resultado.isEmpty(), "Deveria retornar mais de um veterinário");
        assertTrue(resultado.stream().allMatch(v -> v.getSalario().compareTo(salarioMaximo) < 0),
                "Todo veterinário retornado deve ter salário menor que " + salarioMaximo);
    }

    @Test
    void testeBuscarPorSalarioEmFaixaDeValores() {
        BigDecimal salarioMinimo = BigDecimal.valueOf(4000.0);
        BigDecimal salarioMaximo = BigDecimal.valueOf(5000.0);

        List<Veterinario> resultado = repositorio.findBySalarioBetween(salarioMinimo, salarioMaximo);

        assertFalse(resultado.isEmpty(), "Deveria retornar mais de um veterinário");
        assertTrue(
                resultado.stream()
                        .allMatch(v -> v.getSalario().compareTo(salarioMinimo) > 0
                                && v.getSalario().compareTo(salarioMaximo) < 0),
                "Todo veterinário retornado deve ter salário entre " + salarioMinimo + " e " + salarioMaximo);
    }
}