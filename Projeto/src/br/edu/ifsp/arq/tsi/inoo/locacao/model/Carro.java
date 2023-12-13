package br.edu.ifsp.arq.tsi.inoo.locacao.model;

public class Carro {
    protected int carroCodigo = 1; // Nome da variável em minúsculas
    protected String marca;
    protected String modelo;
    protected int ano;
    protected String placa;
    protected int quantidadePortas;
    protected boolean arCondicionado;
    protected double valorDiaria;
    protected boolean estado;
    public Object setD;

    public Carro(int carroCodigo, String marca, String modelo, int ano, String placa, int quantidadePortas,
            boolean arCondicionado, double valorDiaria, boolean estado) {
        this.carroCodigo = carroCodigo; // gerado automaticamente
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.quantidadePortas = quantidadePortas;
        this.arCondicionado = arCondicionado; // tem ou não
        this.valorDiaria = valorDiaria;
        this.estado = estado; // disponivel ou locado (inicialmente disponível)
    }

    public int getCarroCodigo() {
        return carroCodigo; // Removido o incremento ++ para retornar o valor atual
    }

    public void setCarroCodigo(int carroCodigo) {
        this.carroCodigo = carroCodigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

    public boolean isArCondicionado() {
        return arCondicionado;
    }

    public void setArCondicionado(boolean arCondicionado) {
        this.arCondicionado = arCondicionado; // Removido 'false;' antes da atribuição correta
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Carro = [código = " + carroCodigo + ", marca = " + marca + ", modelo = " + modelo + ", ano = " + ano
                + ", placa = " + placa + ", quantidadePortas=" + quantidadePortas + ", arCondicionado=" + arCondicionado
                + ", valor diária = " + valorDiaria + ", disponível = " + estado + "]";
    }

    public void setDisponivel(boolean b) {
    }
}
