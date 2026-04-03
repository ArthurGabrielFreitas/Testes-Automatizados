package br.edu.iftm.testes.atividadea3;

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
        Double valorHora = 100.0;

        funcionario.setValorHora(valorHora);
        funcionario.setHorasTrabalhadas(horasTrabalhadas);
        assertThrows(IllegalArgumentException.class, () -> funcionario.calcularPagamento());
    }
}
