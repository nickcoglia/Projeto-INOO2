// Pessoa class
package br.edu.ifsp.arq.tsi.inoo.locacao.model;

import java.util.ArrayList;

public abstract class Pessoa {
    protected int ClienteCodigo = 1; // Sequencial gerado automaticamente
    protected String nome;
    protected ArrayList<Locacao> locacao;

    public Pessoa(int ClienteCodigo, String nome) {
        this.ClienteCodigo = ClienteCodigo;
        this.nome = nome;
        this.locacao = new ArrayList<>();
    }

    public int getClienteCodigo() {
        return ClienteCodigo;
    }

    public void setClienteCodigo(int codigo) {
        this.ClienteCodigo = codigo;
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
