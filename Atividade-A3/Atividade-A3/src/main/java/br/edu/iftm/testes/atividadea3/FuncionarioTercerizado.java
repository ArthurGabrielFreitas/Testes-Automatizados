package br.edu.iftm.testes.atividadea3;

public class FuncionarioTercerizado extends Funcionario {
    private double valorDespesasAdicionais;

    public void setDespesasAdicionais(double valorDespesasAdicionais) {
        if (valorDespesasAdicionais > 1000) {
            throw new IllegalArgumentException("O valor das despesas adicionais não deve superar R$10000,00");
        }
        this.valorDespesasAdicionais = valorDespesasAdicionais;
    }

    @Override
    public double calcularPagamento() {
        double pagamento = super.getHorasTrabalhadas() * getValorHora() + valorDespesasAdicionais * 1.1;
        if (pagamento > 10000.0) {   
            throw new IllegalArgumentException("Os dados passados geram um pagamento maior que o limite de R$10000,00");
        }
        return pagamento;
    }
    
}
