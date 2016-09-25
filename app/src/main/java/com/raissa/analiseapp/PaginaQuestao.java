package com.raissa.analiseapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by primelan on 5/29/16.
 */
public class PaginaQuestao implements Serializable{
    ArrayList<Questao> questoes;
    ArrayList<String> dicas;

    public ArrayList<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(ArrayList<Questao> questoes) {
        this.questoes = questoes;
    }

    public ArrayList<String> getDicas() {
        return dicas;
    }

    public void setDicas(ArrayList<String> dicas) {
        this.dicas = dicas;
    }
}
