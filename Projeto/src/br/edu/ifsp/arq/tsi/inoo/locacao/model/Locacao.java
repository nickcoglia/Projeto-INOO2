package br.edu.ifsp.arq.tsi.inoo.locacao.model;

import java.time.LocalDate;
import java.util.ArrayList;


public class Locacao {
    private static int contadorNumeracao = 1;

    private int numero;
    private LocalDate dataRealizacao;
    private int numeroDiarias;
    private LocalDate dataDevolucaoMaxima;
    private LocalDate dataDevolucao;
    private ArrayList<PessoaFisica> pessoasFisicas;
    private ArrayList<PessoaJuridica> pessoasJuridicas;
    private ArrayList<Carro> carros;

    public Locacao(LocalDate dataRealizacao, int numeroDiarias) {
        this.numero = contadorNumeracao++;
        this.dataRealizacao = dataRealizacao;
        this.numeroDiarias = numeroDiarias;
        this.dataDevolucaoMaxima = dataRealizacao.plusDays(numeroDiarias);
        this.dataDevolucao = null;

        this.pessoasFisicas = new ArrayList<>();
        this.pessoasJuridicas = new ArrayList<>();
        this.carros = new ArrayList<>();
    }

    public Locacao(PessoaFisica pessoaFisica, Carro carroSelecionado, LocalDate now) {
        this.pessoasFisicas = new ArrayList<>();
        this.pessoasJuridicas = new ArrayList<>();
        this.carros = new ArrayList<>();

        this.pessoasFisicas.add(pessoaFisica);
        this.carros.add(carroSelecionado);
    }

    public Locacao(PessoaJuridica pessoaJuridica, Carro carroSelecionado, LocalDate now) {
        this.pessoasFisicas = new ArrayList<>();
        this.pessoasJuridicas = new ArrayList<>();
        this.carros = new ArrayList<>();

        this.pessoasJuridicas.add(pessoaJuridica);
        this.carros.add(carroSelecionado);
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

    public void setEstadoDevolucao(boolean estado) {
        Object indexCarro;
        if (indexCarro >= 0 && indexCarro < carros.size()) {
            carrosDevolvidos.set(indexCarro, estado);
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
        for (Carro carro : carros) {
            valorTotal += carro.getValorDiaria() * numeroDiarias;
        }
        return valorTotal;
    }

    public boolean locacaoCompleta() {
        return dataDevolucao != null && carros.size() == dataDevolucao.size();
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
        return 0;
    }

    public void setDataDevolucao(LocalDate now) {
    }
    
   
}
