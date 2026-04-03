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
}
