package br.edu.iftm.testes.atividadea3;

public class Funcionario {
    private String nome;
    private int horasTrabalhadas;
    private Double valorHora;

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        if (horasTrabalhadas > 160) {
            throw new IllegalArgumentException("A quantidade de horas trabalhadas por mês deve ser menor que 160");
        } else if (horasTrabalhadas < 20) {
            throw new IllegalArgumentException("A quantidade de horas trabalhadas por mês deve ser maior que 20");
        } 
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public int getHorasTrabalhadas(){
        return horasTrabalhadas;
    }

    public void setValorHora(Double valorHora) {
        if (valorHora > 151.8) {
            throw new IllegalArgumentException("O valor por hora deve ser menor que R$151,80");
        } else if (valorHora < 15.18) {
            throw new IllegalArgumentException("O valor por hora deve ser maior que R$15,18");
        } 
        this.valorHora = valorHora;
    }

    public double getValorHora(){
        return valorHora;
    }

    public double calcularPagamento() {
        double pagamento = horasTrabalhadas * valorHora;
        if (pagamento > 10000.0) {   
            throw new IllegalArgumentException("Os dados passados geram um pagamento maior que o limite de R$10000,00");
        }
        return pagamento;
    }
}
