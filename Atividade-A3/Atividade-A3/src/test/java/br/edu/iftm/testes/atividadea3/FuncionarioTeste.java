package br.edu.iftm.testes.atividadea3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

public class FuncionarioTeste {
    private Funcionario funcionario;

    @BeforeEach
    void instanciarObjetoFuncionarioTercerizado(){
        funcionario = new Funcionario();
    }

    void testarModificarHorasAbaixoDoLimiteInferiorGeraErro() {
        int horasTrabalhadas = 1;

        assertThrows(IllegalArgumentException.class, () -> funcionario.setHorasTrabalhadas(horasTrabalhadas));
    }

    void testarModificarHorasAcimaDoLimiteSuperiorGeraErro(){
        int horasTrabalhadas = 200;

        assertThrows(IllegalArgumentException.class, () -> funcionario.setHorasTrabalhadas(horasTrabalhadas));
    }

    void testarModificarHorasComValoresValidosGeraPagamentoAcimaDoLimiteEGeraErro(){
        int horasTrabalhadas = 160;
        double valorHora = 100.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        assertThrows(IllegalArgumentException.class, () -> funcionario.calcularPagamento());
    }

        void testarModificarHorasComValoresValidosProduzPagamentoEsperado() {
        int horasTrabalhadas = 160;
        double valorHora = 50.0;
        double pagamentoEsperado = 8000.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        double pagamentoObtido = funcionario.calcularPagamento();

        assertEquals(pagamentoEsperado, pagamentoObtido);
    }

    void testarModificarValorPorHoraAbaixoDoLimiteInferiorGeraErro(){
        double valorHora = 10.0;

        assertThrows(IllegalArgumentException.class, () -> funcionario.setValorHora(valorHora));
    }

    void testarModificarValorPorHoraAcimaDoLimiteSuperiorGeraErro(){
        double valorHora = 200.0;

        assertThrows(IllegalArgumentException.class, () -> funcionario.setValorHora(valorHora));
    }

    void testarModificarValorPorHoraComValoresValidosGeraPagamentoAcimaDoLimiteEGeraErro(){
        int horasTrabalhadas = 160;
        double valorHora = 100.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        assertThrows(IllegalArgumentException.class, () -> funcionario.calcularPagamento());
    }

    void testarModificarValorPorHoraComValoresValidosProduzPagamentoEsperado(){
        int horasTrabalhadas = 160;
        double valorHora = 50.0;
        double pagamentoEsperado = 8000.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        double pagamentoObtido = funcionario.calcularPagamento();

        assertEquals(pagamentoEsperado, pagamentoObtido);
    }
}
