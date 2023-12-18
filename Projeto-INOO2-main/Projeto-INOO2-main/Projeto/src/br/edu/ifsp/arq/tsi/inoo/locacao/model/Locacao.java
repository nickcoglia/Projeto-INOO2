package br.edu.ifsp.arq.tsi.inoo.locacao.model;

import java.time.LocalDate;
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


    public Locacao(Pessoa pessoa, Carro carroSelecionado, LocalDate dataRealizacao, int numeroDiarias) {
        this.numero = contadorNumeracao++;
        this.dataRealizacao = dataRealizacao;
        this.numeroDiarias = numeroDiarias;
        //this.dataDevolucaoMaxima = dataRealizacao.plusDays(numeroDiarias);
        this.dataDevolucao = null;

        this.pessoasFisicas = new ArrayList<>();
        this.pessoasJuridicas = new ArrayList<>();
        this.carros = new ArrayList<>();
    }

    public Locacao(PessoaFisica pessoaFisica, Carro carroSelecionado, LocalDate now) {
        this.numero = contadorNumeracao++;
        this.pessoasFisicas = new ArrayList<>();
        this.pessoasJuridicas = new ArrayList<>();
        this.carros = new ArrayList<>();

        this.pessoasFisicas.add(pessoaFisica);
        this.carros.add(carroSelecionado);
        this.dataRealizacao = now;
        this.dataDevolucaoMaxima = now.plusDays(numeroDiarias);
        this.dataDevolucao = null;
    }

    public Locacao(PessoaJuridica pessoaJuridica, Carro carroSelecionado, LocalDate now) {
        this.numero = contadorNumeracao++;
        this.pessoasFisicas = new ArrayList<>();
        this.pessoasJuridicas = new ArrayList<>();
        this.carros = new ArrayList<>();

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

    public double getValorTotal() {
        // Utiliza o método calcularValorTotal() já implementado
        return calcularValorTotal();
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

    public void setValorTotal(double d) {
    }

    public Pessoa getCliente() {
        return null;
    }

    public String getnDiarias() {
        return null;
    }

    public LocalDate getDtDevolucao() {
        return null;
    }
    
}
