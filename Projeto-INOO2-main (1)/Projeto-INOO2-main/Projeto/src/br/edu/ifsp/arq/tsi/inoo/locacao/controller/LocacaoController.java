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
        locacao.setDisponivelDevolucao(indexCarro, true);
        locacao.setDataDevolucao(LocalDate.now());
    }
    

    public boolean verificarDisponivelCarro(Locacao locacao) {
        return locacao.isDisponivelDevolucao();
    }

    // Inner class Locacao
    public static class Locacao {
        private boolean disponivelDevolucao;
        private LocalDate dataDevolucao;

        public void setDisponivelDevolucao(boolean disponivelDevolucao) {
            this.disponivelDevolucao = disponivelDevolucao;
        }

        public boolean isDisponivelDevolucao() {
            return disponivelDevolucao;
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
