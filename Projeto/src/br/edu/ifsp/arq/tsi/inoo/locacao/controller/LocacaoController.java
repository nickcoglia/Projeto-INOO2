package br.edu.ifsp.arq.tsi.inoo.locacao.controller;

import java.time.LocalDate; 
import java.util.ArrayList;

public class LocacaoController {

    private static LocacaoController instance;
    private ArrayList<Locacao> locacoes;

    private LocacaoController() {
        locacoes = new ArrayList<>();
    }

    public static LocacaoController getInstance() {
        if (instance == null) {
            instance = new LocacaoController();
        }
        return instance;
    }

    public boolean save(Locacao locacao) {
        if (locacao != null) {
            return locacoes.add(locacao);
        }
        return false;
    }

    public ArrayList<Locacao> getLocacoes() {
        return locacoes;
    }

    public double calcularValorTotal(int numeroDiarias, double valorDiaria) {
        return valorDiaria * numeroDiarias;
    }

    public static void devolucao(br.edu.ifsp.arq.tsi.inoo.locacao.model.Locacao locacao, int indexCarro) {
        locacao.setEstadoDevolucao(indexCarro, true);
        locacao.setDataDevolucao(LocalDate.now());
    }
    

    public boolean verificarEstadoCarro(Locacao locacao) {
        return locacao.isEstadoDevolucao();
    }

    // Inner class Locacao
    public static class Locacao {
        private boolean estadoDevolucao;
        private LocalDate dataDevolucao;

        public void setEstadoDevolucao(boolean estadoDevolucao) {
            this.estadoDevolucao = estadoDevolucao;
        }

        public boolean isEstadoDevolucao() {
            return estadoDevolucao;
        }

        public LocalDate getDataDevolucao() {
            return dataDevolucao;
        }

        public void setDataDevolucao(LocalDate dataDevolucao) {
            this.dataDevolucao = dataDevolucao;
        }
    }

    public static br.edu.ifsp.arq.tsi.inoo.locacao.model.Locacao buscarLocacaoPorNumero(int numeroLocacao) {
        return null;
    }
}
