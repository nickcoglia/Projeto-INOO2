// PessoaFisica class
package br.edu.ifsp.arq.tsi.inoo.locacao.model;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PessoaFisica extends Pessoa {
    private static Set<String> cpfsRegistrados = new HashSet<>();
    private String cpf;

    public PessoaFisica(String nome, String cpf) {
        super(nome);
        if (!cpfJaRegistrado(cpf)) {
            this.cpf = cpf;
            cpfsRegistrados.add(cpf);
        } else {
            throw new IllegalArgumentException("CPF já registrado!");
        }
    }

    private boolean cpfJaRegistrado(String cpf) {
        return cpfsRegistrados.contains(cpf);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (!cpfJaRegistrado(cpf)) {
            cpfsRegistrados.remove(this.cpf);
            this.cpf = cpf;
            cpfsRegistrados.add(cpf);
        } else {
            throw new IllegalArgumentException("CPF já registrado!");
        }
    }

    @Override
    public String toString() {
        return "Pessoa Física: [código = " + getCodigo() + ", nome = " + getNome() + ", cpf = " + cpf + " ]";
    }
}
