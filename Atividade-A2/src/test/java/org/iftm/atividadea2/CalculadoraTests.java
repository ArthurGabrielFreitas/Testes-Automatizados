package org.iftm.atividadea2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CalculadoraTests {

    private Calculadora calculadora;

    @Nested
    @DisplayName("Inicialização")
    class MemoriaTests {

        @Test
        @DisplayName("A memória deve ser inicializada corretamente sem passar valor")
        void testeConstrutorSemArgumentos() {
            // Arrange
            int memoriaEsperada = 0;
            calculadora = new Calculadora();

            // Act
            int memoria = calculadora.getMemoria();

            // Assert
            assertEquals(memoriaEsperada, memoria);
        }

        @Test
        @DisplayName("A memória deve ser inicializada corretamente passando um valor positivo")
        void testeConstrutorComArgumentoPositivo() {
            // Arrange
            int valorDaMemoria = 3;
            calculadora = new Calculadora(valorDaMemoria);

            // Act
            int memoria = calculadora.getMemoria();

            // Assert
            assertEquals(valorDaMemoria, memoria);
        }

        @Test
        @DisplayName("A memória deve ser inicializada corretamente passando um valor negativo")
        void testeConstrutorComArgumentoNegativo() {
            // Arrange
            int valorDaMemoria = -3;
            calculadora = new Calculadora(valorDaMemoria);

            // Act
            int memoria = calculadora.getMemoria();

            // Assert
            assertEquals(valorDaMemoria, memoria);
        }

    }

    @Nested
    @DisplayName("Operações")
    class OperacoesTests {

        @BeforeEach
        void setup() {
            calculadora = new Calculadora(3);
        }

        @Test
        @DisplayName("Deve somar corretamente um número positivo")
        void testeSomarPositivo() {
            // Arrange
            int valorASomar = 4;
            int resultadoEsperado = 7;

            // Act
            calculadora.somar(valorASomar);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve somar corretamente um número negativo")
        void testeSomarNegativo() {
            // Arrange
            int valorASomar = -4;
            int resultadoEsperado = -1;

            // Act
            calculadora.somar(valorASomar);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve subtrair corretamente um número positivo")
        void testeSubtrairPositivo() {
            // Arrange
            int valorASubtrair = 4;
            int resultadoEsperado = -1;

            // Act
            calculadora.subtrair(valorASubtrair);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve subtrair corretamente um número negativo")
        void testeSubtrairNegativo() {
            // Arrange
            int valorASubtrair = -4;
            int resultadoEsperado = 7;

            // Act
            calculadora.subtrair(valorASubtrair);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve multiplicar corretamente um número positivo")
        void testeMultiplicarPositivo() {
            // Arrange
            int valorAMultiplicar = 4;
            int resultadoEsperado = 12;

            // Act
            calculadora.multiplicar(valorAMultiplicar);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve multiplicar corretamente um número negativo")
        void testeMultiplicarNegativo() {
            // Arrange
            int valorAMultiplicar = -4;
            int resultadoEsperado = -12;

            // Act
            calculadora.multiplicar(valorAMultiplicar);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve dividir corretamente um número positivo")
        void testeDividirPositivo() throws Exception {
            // Arrange
            int valorADividir = 4;
            int resultadoEsperado = 0;

            // Act
            calculadora.dividir(valorADividir);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve dividir corretamente um número negativo")
        void testeDividirNegativo() throws Exception {
            // Arrange
            int valorADividir = -4;
            int resultadoEsperado = 0;

            // Act
            calculadora.dividir(valorADividir);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve lançar Exception ao dividir por zero")
        void testeDividirPorZero() {
            // Arrange
            int divisor = 0;

            // Assert
            assertThrows(Exception.class, () -> calculadora.dividir(divisor));
        }

        @Test
        @DisplayName("Deve exponenciar corretamente para expoente 1")
        void testeExponenciarExpoente1() throws Exception {
            // Arrange
            int expoente = 1;
            int resultadoEsperado = 3;

            // Act
            calculadora.exponenciar(expoente);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve exponenciar corretamente para expoente 10")
        void testeExponenciarExpoente10() throws Exception {
            // Arrange
            int expoente = 10;
            int resultadoEsperado = 59049;

            // Act
            calculadora.exponenciar(expoente);
            int resultadoObtido = calculadora.getMemoria();

            // Assert
            assertEquals(resultadoEsperado, resultadoObtido);
        }

        @Test
        @DisplayName("Deve lançar Exception para expoente 20")
        void testeExponenciarExpoente20() {
            // Arrange
            int expoente = 20;

            // Assert
            assertThrows(Exception.class, () -> calculadora.exponenciar(expoente));
        }

        @Test
        @DisplayName("Deve zerar corretamente a memória")
        void testeZerarMemoria() {
            // Arrange
            int memoriaEsperada = 0;

            // Act
            calculadora.zerarMemoria();
            int memoria = calculadora.getMemoria();

            // Assert
            assertEquals(memoriaEsperada, memoria);
        }
    }
}
