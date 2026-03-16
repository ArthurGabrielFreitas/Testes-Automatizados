package org.iftm.atividadea2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadoraTests {
    @Test
    @DisplayName("A memória deve ser inicializada corretamente sem passar valor")
    void testeConstrutorSemArgumentos() {
        // Arrange
        Calculadora calculadora = new Calculadora();
        int memoria = calculadora.getMemoria();

        // Assert
        assertEquals(0, memoria);
    }

    @Test
    @DisplayName("A memória deve ser inicializada corretamente passando um valor")
    void testeConstrutorComArgumentos() {
        // Arrange
        int valorDaMemoria1 = 3;
        int valorDaMemoria2 = -3;

        Calculadora calculadora1 = new Calculadora(valorDaMemoria1);
        int memoria1 = calculadora1.getMemoria();

        Calculadora calculadora2 = new Calculadora(valorDaMemoria2);
        int memoria2 = calculadora2.getMemoria();

        // Assert
        assertEquals(valorDaMemoria1, memoria1);
        assertEquals(valorDaMemoria2, memoria2);
    }

    @Test
    @DisplayName("Deve somar corretamente um número positivo e outro negativo")
    void testeSomar() {
        // Arrange
        int valorNegativo = -4;
        Calculadora calculadora = new Calculadora(3);
        int resultadoEsperado = -1;

        // Act
        calculadora.somar(valorNegativo);
        int resultadoObtido = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    @Test
    @DisplayName("Deve subtrair corretamente um número positivo por outro negativo")
    void testeSubtrair() {
        // Arrange
        int valorNegativo = -4;
        Calculadora calculadora = new Calculadora(3);
        int resultadoEsperado = 7;

        // Act
        calculadora.subtrair(valorNegativo);
        int resultadoObtido = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    @Test
    @DisplayName("Deve multiplicar corretamente um número positivo por outro negativo")
    void testeMultiplicar() {
        // Arrange
        int valorNegativo = -4;
        Calculadora calculadora = new Calculadora(3);
        int resultadoEsperado = -12;

        // Act
        calculadora.multiplicar(valorNegativo);
        int resultadoObtido = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    @Test
    @DisplayName("Deve dividir corretamente um número positivo por outro negativo. Além disso, deve apresentar Exception ao dividir por 0")
    void testeDividir() throws Exception{
        // Arrange
        int valorNegativo = -4;
        Calculadora calculadora = new Calculadora(3);
        int resultadoEsperado = 0;

        // Act
        calculadora.dividir(valorNegativo);
        int resultadoObtido = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoObtido);
        assertThrows(Exception.class, () -> calculadora.dividir(0));
    }

    @Test
    @DisplayName("Deve exponenciar corretamente e lançar Exception caso o exponente for maior que 20")
    void testeExponenciar() throws Exception {
        // Arrage
        int exponente1 = 10;
        int exponente2 = 20;
        Calculadora calculadora = new Calculadora(1);
        int resultadoEsperado = 1;

        // Act
        calculadora.exponenciar(exponente1);
        int resultadoObtido = 1;

        // Assert
        assertEquals(resultadoEsperado, resultadoObtido);
        assertThrows(Exception.class, () -> calculadora.exponenciar(exponente2));
    }

    @Test
    @DisplayName("Deve zerar corretamente a memória")
    void testeZerarMemoria() {
        // Arrange
        Calculadora calculadora = new Calculadora(3);

        // Act
        calculadora.zerarMemoria();
        int memoria = calculadora.getMemoria();

        // Assert
        assertEquals(0, memoria);
    }
}
