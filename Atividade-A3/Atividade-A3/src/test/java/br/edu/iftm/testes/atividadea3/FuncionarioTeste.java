package br.edu.iftm.testes.atividadea3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FuncionarioTeste {
    private Funcionario funcionario;

    @BeforeEach
    void instanciarObjetoFuncionarioTercerizado() {
        funcionario = new Funcionario();
    }

    @Test
    @DisplayName("Deve lançar erro ao modificar horas abaixo do limite inferior")
    void testarModificarHorasAbaixoDoLimiteInferiorGeraErro() {
        int horasTrabalhadas = 1;

        assertThrows(IllegalArgumentException.class, () -> funcionario.setHorasTrabalhadas(horasTrabalhadas));
    }

    @Test
    @DisplayName("Deve lançar erro ao modificar horas acima do limite superior")
    void testarModificarHorasAcimaDoLimiteSuperiorGeraErro() {
        int horasTrabalhadas = 200;

        assertThrows(IllegalArgumentException.class, () -> funcionario.setHorasTrabalhadas(horasTrabalhadas));
    }

    @Test
    @DisplayName("Deve lançar erro ao calcular pagamento acima do limite com horas válidas")
    void testarModificarHorasComValoresValidosGeraPagamentoAcimaDoLimiteEGeraErro() {
        int horasTrabalhadas = 160;
        double valorHora = 100.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        assertThrows(IllegalArgumentException.class, () -> funcionario.calcularPagamento());
    }

    @Test
    @DisplayName("Deve produzir pagamento esperado com horas válidas")
    void testarModificarHorasComValoresValidosProduzPagamentoEsperado() {
        int horasTrabalhadas = 160;
        double valorHora = 50.0;
        double pagamentoEsperado = 8000.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        double pagamentoObtido = funcionario.calcularPagamento();

        assertEquals(pagamentoEsperado, pagamentoObtido);
    }

    @Test
    @DisplayName("Deve lançar erro ao modificar valor por hora abaixo do limite inferior")
    void testarModificarValorPorHoraAbaixoDoLimiteInferiorGeraErro() {
        double valorHora = 10.0;

        assertThrows(IllegalArgumentException.class, () -> funcionario.setValorHora(valorHora));
    }

    @Test
    @DisplayName("Deve lançar erro ao modificar valor por hora acima do limite superior")
    void testarModificarValorPorHoraAcimaDoLimiteSuperiorGeraErro() {
        double valorHora = 200.0;

        assertThrows(IllegalArgumentException.class, () -> funcionario.setValorHora(valorHora));
    }

    @Test
    @DisplayName("Deve lançar erro ao calcular pagamento acima do limite com valor por hora válido")
    void testarModificarValorPorHoraComValoresValidosGeraPagamentoAcimaDoLimiteEGeraErro() {
        int horasTrabalhadas = 160;
        double valorHora = 100.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        assertThrows(IllegalArgumentException.class, () -> funcionario.calcularPagamento());
    }

    @Test
    @DisplayName("Deve produzir pagamento esperado com valor por hora válido")
    void testarModificarValorPorHoraComValoresValidosProduzPagamentoEsperado() {
        int horasTrabalhadas = 160;
        double valorHora = 50.0;
        double pagamentoEsperado = 8000.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        double pagamentoObtido = funcionario.calcularPagamento();

        assertEquals(pagamentoEsperado, pagamentoObtido);
    }
}
