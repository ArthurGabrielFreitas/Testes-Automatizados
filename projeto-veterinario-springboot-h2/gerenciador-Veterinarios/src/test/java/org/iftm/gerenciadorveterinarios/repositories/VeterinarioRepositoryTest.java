package org.iftm.gerenciadorveterinarios.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;

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

        assertFalse(resultado.isEmpty(), "Deveria retornar pelo menos um veterinário");
        assertTrue(resultado.stream().allMatch(v -> v.getSalario().compareTo(salarioMinimo) > 0),
                "Todo veterinário retornado deve ter salário maior que " + salarioMinimo);
    }

    @Test
    void testeBuscarPorSalarioInferior() {
        BigDecimal salarioMaximo = BigDecimal.valueOf(5000.0);

        List<Veterinario> resultado = repositorio.findBySalarioLessThan(salarioMaximo);

        assertFalse(resultado.isEmpty(), "Deveria retornar pelo menos um veterinário");
        assertTrue(resultado.stream().allMatch(v -> v.getSalario().compareTo(salarioMaximo) < 0),
                "Todo veterinário retornado deve ter salário menor que " + salarioMaximo);
    }

    @Test
    void testeBuscarPorSalarioEmFaixaDeValores() {
        BigDecimal salarioMinimo = BigDecimal.valueOf(4000.0);
        BigDecimal salarioMaximo = BigDecimal.valueOf(5000.0);

        List<Veterinario> resultado = repositorio.findBySalarioBetween(salarioMinimo, salarioMaximo);

        assertFalse(resultado.isEmpty(), "Deveria retornar pelo menos um veterinário");
        assertTrue(
                resultado.stream()
                        .allMatch(v -> v.getSalario().compareTo(salarioMinimo) >= 0),
                "Todo veterinário retornado deve ter maior que " + salarioMinimo);
        assertTrue(
                resultado.stream()
                        .allMatch(v -> v.getSalario().compareTo(salarioMinimo) >= 0
                                && v.getSalario().compareTo(salarioMaximo) <= 0),
                "Todo veterinário retornado deve ter salário menor que " + salarioMaximo);
    }

    @Test
    void testeBuscarDataDeNascimentoEmFaixaDeValores() {
        Instant dataMinima = Instant.parse("2000-01-01T00:00:00Z");
        Instant hoje = Instant.now();

        List<Veterinario> resultado = repositorio.findByDataNascimentoBetween(dataMinima, hoje);

        assertFalse(resultado.isEmpty(), "Deveria retornar pelo menos um veterinário");
        assertTrue(resultado.stream().allMatch(
                v -> v.getDataNascimento().compareTo(dataMinima) >= 0),
                "A data de nascimento dos veterinários retornados deve ser posterior a "
                        + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"))
                                .withLocale(new Locale("pt", "BR")));
        assertTrue(resultado.stream().allMatch(
                v -> v.getDataNascimento().compareTo(hoje) <= 0),
                "A data de nascimento dos veterinários retornados deve ser anterior a "
                        + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"))
                                .withLocale(new Locale("pt", "BR")));
    }

    @Test
    @Commit
    void testeAtualizarBancoDeDados() {
        String silabaBusca = "iza";
        String nomeNovo = "Ana Beatriz Borges";
        String silabaBuscaNova = "atriz";
        String emailNovo = "anabeatriz@gmail.com";
        BigDecimal salarioMinimo = BigDecimal.valueOf(3800.0);
        Instant dataNova = Instant.parse("1995-03-15T00:00:00Z");

        Instant dataMinima = Instant.parse("2000-01-01T00:00:00Z");
        Instant hoje = Instant.now();

        Veterinario vetAtualizado = repositorio.findByNomeContains(silabaBusca).get(0);
        vetAtualizado.setNome(nomeNovo);
        vetAtualizado.setEmail(emailNovo);
        vetAtualizado.setSalario(salarioMinimo);
        vetAtualizado.setDataNascimento(dataNova);

        repositorio.save(vetAtualizado);
        List<Veterinario> resultadoBuscaPorNomeAntigoAposAtualizar = repositorio.findByNomeContains(silabaBusca);
        List<Veterinario> resultadoBuscaPorDataAposAtualizar = repositorio.findByDataNascimentoBetween(dataMinima,
                hoje);

        List<Veterinario> resultadoBuscaPorNomeNovoAposAtualizar = repositorio.findByNomeContains(silabaBuscaNova);

        assertTrue(resultadoBuscaPorNomeAntigoAposAtualizar.isEmpty(),
                "A veterinária atualizada não deve aparecer nesse filtro");
        assertTrue(resultadoBuscaPorDataAposAtualizar.stream().allMatch(v -> !v.getEmail().equals(emailNovo)),
                "A veterinária atualizada não deve aparecer nesse filtro");
        assertFalse(resultadoBuscaPorNomeNovoAposAtualizar.isEmpty());
    }

    @Test
    void testeBuscarQuantosVeterinariosAcimaDoTetoSalarial() {
        int quantidadeAcimaDoTeto = 8;
        BigDecimal tetoSalarial = BigDecimal.valueOf(5000);

        int resultado = repositorio.countBySalarioGreaterThan(tetoSalarial);

        assertEquals(quantidadeAcimaDoTeto, resultado);
    }

    @Test
    void testeRecusarSalvarEmailDuplicado() {
        String silabaBusca = "iza";
        String nomeNovo = "Ana Beatriz Borges";
        String especialidadeNova = "grandes";
        BigDecimal salarioMinimo = BigDecimal.valueOf(3800.0);
        Instant dataNova = Instant.parse("1995-03-15T00:00:00Z");
        Veterinario vetNovo = new Veterinario();

        Veterinario vetSalvo = repositorio.findByNomeContains(silabaBusca).get(0);
        vetNovo.setNome(nomeNovo);
        vetNovo.setEspecialidade(especialidadeNova);
        vetNovo.setSalario(salarioMinimo);
        vetNovo.setDataNascimento(dataNova);
        vetNovo.setEmail(vetSalvo.getEmail());

        assertThrows(DataIntegrityViolationException.class,
                () -> {
                    repositorio.save(vetNovo);
                    repositorio.flush();
                }, "Não deveria permitir o salvamento");
    }
}