package br.edu.ifsp.arq.tsi.inoo.locacao.model;

import java.time.LocalDate;
//import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Locacao {
    private static int contadorNumeracao = 1;

    protected int numero;
    protected LocalDate dataRealizacao;
    protected int numeroDiarias;
    protected LocalDate dataDevolucaoMaxima;
    protected LocalDate dataDevolucao;
    protected ArrayList<PessoaFisica> pessoasFisicas;
    protected ArrayList<PessoaJuridica> pessoasJuridicas;
    protected ArrayList<Carro> carros;
    protected boolean devolvido; // Adicionando um campo para controlar disponivel de devolução
    private double valorTotal;

    /*
     * public Locacao(Pessoa pessoa, Carro carroSelecionado, LocalDate
     * dataRealizacao, int numeroDiarias) {
     * this.numero = contadorNumeracao++;
     * this.dataRealizacao = dataRealizacao;
     * this.numeroDiarias = numeroDiarias;
     * this.dataDevolucaoMaxima = dataRealizacao.plusDays(numeroDiarias);
     * this.dataDevolucao = null;
     * this.carros = new ArrayList<>();
     * 
     * this.pessoasFisicas = new ArrayList<>();
     * this.pessoasJuridicas = new ArrayList<>();
     * this.carros.add(carroSelecionado);
     * }
     */
    public Locacao(PessoaFisica pessoaFisica, Carro carroSelecionado, LocalDate now, int numeroDiarias) {
        this.numero = contadorNumeracao++;
        this.pessoasFisicas = new ArrayList<>();
        this.pessoasJuridicas = new ArrayList<>();
        this.carros = new ArrayList<>();

        this.numeroDiarias = numeroDiarias;
        this.pessoasFisicas.add(pessoaFisica);
        this.carros.add(carroSelecionado);
        this.dataRealizacao = now;
        this.dataDevolucaoMaxima = now.plusDays(numeroDiarias);
        this.dataDevolucao = null;
    }

    public Locacao(PessoaJuridica pessoaJuridica, Carro carroSelecionado, LocalDate now, int numeroDiarias) {
        this.numero = contadorNumeracao++;
        this.pessoasFisicas = new ArrayList<>();
        this.pessoasJuridicas = new ArrayList<>();
        this.carros = new ArrayList<>();

        this.numeroDiarias = numeroDiarias;
        this.pessoasJuridicas.add(pessoaJuridica);
        this.carros.add(carroSelecionado);
        this.dataRealizacao = now;
        this.dataDevolucaoMaxima = now.plusDays(numeroDiarias);
        this.dataDevolucao = null;
    }

    public Pessoa getPessoa() {
        if (!pessoasFisicas.isEmpty()) {
            return pessoasFisicas.get(0); // Retorna a primeira pessoa física associada à locação
        } else if (!pessoasJuridicas.isEmpty()) {
            return pessoasJuridicas.get(0); // Retorna a primeira pessoa jurídica associada à locação
        } else {
            return null; // Retorna null se não houver pessoa associada à locação
        }
    }

    public Carro getCarro() {
        if (!carros.isEmpty()) {
            return carros.get(0); // Retorna o primeiro carro associado à locação
        } else {
            return null; // Retorna null se não houver carro associado à locação
        }
    }

    public LocalDate getDataLocacao() {
        return dataRealizacao; // Retorna a data de realização da locação
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public LocalDate getDataDevolucaoMaxima() {
        return dataDevolucaoMaxima;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDisponivelDevolucao(int indexCarro, boolean disponivel) {
        if (indexCarro >= 0 && indexCarro < carros.size()) {
            carros.get(indexCarro).setDisponivel(disponivel);
        } else {
            System.out.println("Índice de carro inválido!");
        }
    }

    public double calcularValorTotal() {
        double valorTotal = 0.0;

        if (dataRealizacao != null && dataDevolucao != null) {
            long diasLocados = ChronoUnit.DAYS.between(dataRealizacao, dataDevolucao);

            for (Carro carro : carros) {
                valorTotal += carro.getValorDiaria() * diasLocados;
            }
        }

        return valorTotal;
    }

    public boolean locacaoCompleta() {
        for (Carro carro : carros) {
            if (carro.getDataDevolucao() == null) {
                return false; // Se algum carro não foi devolvido, a locação não está completa
            }
        }
        return true; // Todos os carros foram devolvidos, locação completa
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }

    @Override
    public String toString() {
        return "Locacao{" +
                "numero=" + numero +
                ", dataRealizacao=" + dataRealizacao +
                ", numeroDiarias=" + numeroDiarias +
                ", dataDevolucaoMaxima=" + dataDevolucaoMaxima +
                ", dataDevolucao=" + dataDevolucao +
                ", pessoasFisicas=" + pessoasFisicas +
                ", pessoasJuridicas=" + pessoasJuridicas +
                ", carros=" + carros +
                '}';
    }

    public int getNumero() {
        return numero;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Pessoa getCliente() {
        return null;
    }

    public Integer getnDiarias() {
        return this.numeroDiarias;
    }

    public LocalDate getDtDevolucao() {
        return null;
    }

    public void setValorTotal(double valorTotal) {
        // Armazenar o valor total da locação
        this.valorTotal = valorTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double calcularValorTotal(LocalDate dataDevolucao) {
        if (dataDevolucao.isBefore(this.dataRealizacao)) {
            System.out.println("Data de devolução inválida! Deve ser após a data de realização!");
            return 0.0; // ou outro valor que indique um erro
        }

        long diasLocados = ChronoUnit.DAYS.between(this.dataRealizacao, dataDevolucao);
        double valorTotal = 0.0;

        for (Carro carro : carros) {
            valorTotal += carro.getValorDiaria() * diasLocados;
        }

        return valorTotal;
    }

    public void realizarDevolucao(LocalDate dataDevolucao2) {
    }

    public int getNumeroDiarias() {
        return 0;
    }

}
