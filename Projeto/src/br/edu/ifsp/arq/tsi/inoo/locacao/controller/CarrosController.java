package br.edu.ifsp.arq.tsi.inoo.locacao.controller;

import java.util.ArrayList;

import br.edu.ifsp.arq.tsi.inoo.locacao.model.Carro;

public class CarrosController {

    private static CarrosController instance;
    private ArrayList<Carro> carros;

    private CarrosController() {
        carros = new ArrayList<>();
    }

    public static CarrosController getInstance() {
        if (instance == null) {
            instance = new CarrosController();
        }
        return instance;
    }

    public boolean saveCarro(Carro carro) {
        if (carros != null) {
            return carros.add(carro);
        }
        return false;
    }

    public ArrayList<Carro> getCarros() {
        return carros;
    }

    public double getValorDiaria() {
        return 0;
    }
}
