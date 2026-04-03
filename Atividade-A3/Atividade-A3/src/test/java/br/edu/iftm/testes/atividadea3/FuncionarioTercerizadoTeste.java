package br.edu.iftm.testes.atividadea3;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        double valorDespesasAdicionais = 2000.0;

        assertThrows(IllegalArgumentException.class, () -> funcionarioTercerizado.setDespesasAdicionais(valorDespesasAdicionais));
    }

    @Test
    void testeModificarDespesaGeraPagamentoAcimaDoLimiteDeSalarioGerandoErro() {
        double valorDespesasAdicionais = 1000.0;
        double valorHora = 60.0;
        int horasTrabalhadas = 160;

        funcionarioTercerizado.setDespesasAdicionais(valorDespesasAdicionais);
        funcionarioTercerizado.setValorHora(valorHora);
        funcionarioTercerizado.setHorasTrabalhadas(horasTrabalhadas);
        assertThrows(IllegalArgumentException.class, () -> funcionarioTercerizado.calcularPagamento());
    }

    @Test
    void testeModificarDespesaGeraPagamentoValido() {
        double valorDespesasAdicionais = 200.0;
        double valorHora = 40.0;
        int horasTrabalhadas = 160;
        double pagamentoEsperado = 6620.0;

        funcionarioTercerizado.setDespesasAdicionais(valorDespesasAdicionais);
        funcionarioTercerizado.setValorHora(valorHora);
        funcionarioTercerizado.setHorasTrabalhadas(horasTrabalhadas);
        double pagamentoObtido = funcionarioTercerizado.calcularPagamento();

        assertEquals(pagamentoObtido, pagamentoEsperado);
    }
}
