package br.edu.iftm.testes.atividadea3;

import org.junit.jupiter.api.BeforeEach;

public class FuncionarioTeste {
    private Funcionario funcionario;

    @BeforeEach
    void instanciarObjetoFuncionarioTercerizado(){
        funcionario = new Funcionario();
    }
}
