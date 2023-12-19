package br.edu.ifsp.arq.tsi.inoo.locacao.model;

import java.util.ArrayList;

public abstract class Pessoa {
    protected static int proximoCodigo = 0; // Sequencial gerado automaticamente
    protected int ClienteCodigo;
    protected String nome;
    protected ArrayList<Locacao> locacao;

    public Pessoa(String nome) {
        this.ClienteCodigo = proximoCodigo++;
        this.nome = nome;
        this.locacao = new ArrayList<>();
    }

    public int getProximoCodigo() {
        return proximoCodigo;
    }

    public void setProximoCodigo(int codigo) {
        proximoCodigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return ClienteCodigo;
    }

    @Override
    public String toString() {
        return "Pessoa [ClienteCodigo=" + ClienteCodigo + ", nome=" + nome + "]";
    }
}
