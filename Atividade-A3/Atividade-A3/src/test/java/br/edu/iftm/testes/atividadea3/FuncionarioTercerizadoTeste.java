package br.edu.iftm.testes.atividadea3;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FuncionarioTercerizadoTeste {
    @BeforeEach
    void instanciarObjetoFuncionarioTercerizado(){
        final FuncionarioTercerizado funcionarioTercerizado = new FuncionarioTercerizado();
    }
}
