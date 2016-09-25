package com.raissa.analiseapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by primelan on 5/29/16.
 */
public class Resposta implements Serializable {
    String texto;
    int valor;
    ArrayList<Questao> questoes;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public ArrayList<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(ArrayList<Questao> questoes) {
        this.questoes = questoes;
    }
}
