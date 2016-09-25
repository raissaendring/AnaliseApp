package com.raissa.analiseapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by primelan on 5/29/16.
 */
public class Questao implements Serializable{
    String texto;
    ArrayList<Resposta> respostas;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ArrayList<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(ArrayList<Resposta> respostas) {
        this.respostas = respostas;
    }
}
