package br.edu.iftm.testes.atividadea3;

public class FuncionarioTercerizado extends Funcionario {
    private double valorDespesasAdicionais;

    public void setDespesasAdicionais(Double valorDespesasAdicionais) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDespesasAdicionais'");
    }

    @Override
    public double calcularPagamento() {
        double pagamento = super.getHorasTrabalhadas() * getValorHora() + valorDespesasAdicionais * 1.1;
        if (pagamento > 1000.0) {   
            throw new IllegalArgumentException("Os dados passados geram um pagamento maior que o limite de R$10000,00");
        }
        return pagamento;
    }
    
}
