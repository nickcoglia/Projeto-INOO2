package br.edu.ifsp.arq.tsi.inoo.locacao.model;

public class PessoaJuridica extends Pessoa {
    private String cnpj;
    private String razaoSocial;

    public PessoaJuridica(String nome, String cnpj, String razaoSocial) {
        super(nome);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String toString() {
        return "Pessoa Jurídica = [código = " + getCodigo() + ", nome = " + getNome() + ", cnpj = " + cnpj
                + ", razaoSocial = " + razaoSocial + "]";
    }
}
