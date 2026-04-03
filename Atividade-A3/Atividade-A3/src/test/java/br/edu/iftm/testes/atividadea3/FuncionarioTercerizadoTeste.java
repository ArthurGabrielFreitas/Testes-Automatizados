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
        Double valorDespesasAdicionais = 2000.0;

        assertThrows(IllegalArgumentException.class, () -> funcionarioTercerizado.setDespesasAdicionais(valorDespesasAdicionais));
    }

    @Test
    void testeModificarDespesaGeraPagamentoAcimaDoLimiteDeSalarioGerandoErro() {
        Double valorDespesasAdicionais = 1000.0;
        Double valorHora = 60.0;
        int horasTrabalhadas = 160;

        funcionarioTercerizado.setDespesasAdicionais(valorDespesasAdicionais);
        funcionarioTercerizado.setValorHora(valorHora);
        funcionarioTercerizado.setHorasTrabalhadas(horasTrabalhadas);
        assertThrows(IllegalArgumentException.class, () -> funcionarioTercerizado.calcularPagamento());
    }
}
