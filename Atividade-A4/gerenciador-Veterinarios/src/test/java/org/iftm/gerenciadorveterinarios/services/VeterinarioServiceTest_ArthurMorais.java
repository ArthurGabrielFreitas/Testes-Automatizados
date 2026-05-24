package test.java.org.iftm.gerenciadorveterinarios.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.iftm.gerenciadorveterinarios.servicies.VeterinarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VeterinarioServiceTest_ArthurMorais {

    @Mock
    private VeterinarioRepository veterinarioRepository;

    @InjectMocks
    private VeterinarioService veterinarioService;

    @Test
    @DisplayName("Deve retornar uma lista com apenas dois veterinários")
    public void testeBuscaVeterinariosComParteNome() {
        Veterinario veterinario1 = new Veterinario(1, "Marco José da Silva", "marjo@email.com", "cachorros", BigDecimal.valueOf(2500));
        Veterinario veterinario2 = new Veterinario(2, "Maryanny Lopes Silva", "marlo@email.com", "gatos", BigDecimal.valueOf(5500));
        Veterinario veterinario3 = new Veterinario(3, "Maríssia Mauro Maury", "marmaumau@email.com", "exóticos", BigDecimal.valueOf(3500));
        List<Veterinario> vets = Arrays.asList(veterinario1, veterinario2);

        when(veterinarioService.buscaVeterinariosComParteNome("Silva")).thenReturn(vets);

        List<Veterinario> resultado = veterinarioService.buscaVeterinariosComParteNome("Silva");

        assertEquals(2, resultado.size());
        assertEquals(vets, resultado);
        assertFalse(resultado.contains(veterinario3));
        verify(veterinarioRepository).findByNomeContains("Silva");
    }

    
    @Test
    @DisplayName("Deve lançar exceção caso não encontre o id")
    public void deveLancarExcecaoAoApagarQuandoIdNaoExistir() {
        Integer idInexistente = 9999;

        when(veterinarioService.apagarPorId(idInexistente)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            veterinarioService.apagarPorId(idInexistente);
        });
    }

}
