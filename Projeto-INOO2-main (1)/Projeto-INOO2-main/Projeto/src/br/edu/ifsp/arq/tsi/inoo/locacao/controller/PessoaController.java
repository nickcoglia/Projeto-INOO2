package br.edu.ifsp.arq.tsi.inoo.locacao.controller;

import java.util.ArrayList;

import br.edu.ifsp.arq.tsi.inoo.locacao.model.Pessoa;

public class PessoaController {

    private static PessoaController instance;
    private ArrayList<Pessoa> pessoas;

    private PessoaController(){
        pessoas = new ArrayList<>();
    }

    public static PessoaController getInstance(){
        if(instance == null){
            instance = new PessoaController();
        }
        return instance;
    }

    public boolean save(Pessoa pessoa){
        if(pessoas != null){
            return pessoas.add(pessoa);
        }
        return false;
    }

    public ArrayList<Pessoa> getPessoa() {
        return pessoas;
    }
}
