package br.edu.iftm.testes.atividadea3;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FuncionarioTercerizadoTeste {
    private FuncionarioTercerizado funcionarioTercerizado;

    @BeforeEach
    void instanciarObjetoFuncionarioTercerizado(){
        funcionarioTercerizado = new FuncionarioTercerizado();
    }

    @Test
    void testeModificarDespesaAcimaDoLimiteGeraErro() {
        int valorDespesasAdicionais = 2000;

        assertThrows(IllegalArgumentException.class, () -> funcionarioTercerizado.setDespesasAdicionais(valorDespesasAdicionais));
    }
}
