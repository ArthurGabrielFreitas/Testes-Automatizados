package org.iftm.gerenciadorveterinarios.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}