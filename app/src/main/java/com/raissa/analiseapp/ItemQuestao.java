package com.raissa.analiseapp;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by primelan on 9/3/15.
 */

public class ItemQuestao implements Serializable{
    public enum respostasP {Sim, Nao, Indiferente;

        @Override
        public String toString() {
            if(this==Sim) return "Sim";
            else if(this==Nao){ return "Não";}
            else return "";
        }
    }
    public enum respostasS {Pouco,Muito,Indiferente;

        @Override
        public String toString() {
            switch (this){
                case Pouco: return "Pouco";
                case Muito: return "Muito";
                case Indiferente: return "";
                default: return "";
            }
        }
    }

    transient Context context;
    int id;
    String perguntaPrincipal;
    String pergutaSecundária;
    respostasP respostaPrimaria;
    respostasS respostaSecundaria;
    int situacao;
    int[] classificacoes;
    float tamanho;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPerguntaPrincipal(String perguntaPrincipal) {
        this.perguntaPrincipal = perguntaPrincipal;
    }

    public void setPergutaSecundária(String pergutaSecundária) {
        this.pergutaSecundária = pergutaSecundária;
    }

    public int[] getClassificacoes() {
        return classificacoes;
    }

    public void setClassificacoes(int[] classificacoes) {
        this.classificacoes = classificacoes;
    }

    public ItemQuestao(){}

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public float getTamanho() {

        return tamanho;
    }

    public ItemQuestao (Context c, int id, String pp, String ps){
        this.id=id;
        this.perguntaPrincipal = pp;
        this.pergutaSecundária=ps;
        this.situacao=0;
        this.respostaPrimaria=respostasP.Indiferente;
        this.respostaSecundaria=respostasS.Indiferente;
        context = c;
        tamanho=0;

    }

    public void setRespostaPrimaria(respostasP respostaPrimaria) {
        this.respostaPrimaria = respostaPrimaria;
    }

    public void setRespostaSecundaria(respostasS respostaSecundaria) {
        this.respostaSecundaria = respostaSecundaria;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public int getSituacao() {

        return situacao;
    }

    public respostasS getRespostaSecundaria() {
        return respostaSecundaria;
    }

    public respostasP getRespostaPrimaria() {
        return respostaPrimaria;
    }

    public String getPergutaSecundária() {
        return pergutaSecundária;
    }

    public String getPerguntaPrincipal() {
        return perguntaPrincipal;
    }

    public int somaRespostas(ArrayList<ItemQuestao> listaQuestoes){
        int soma=0;
        for(ItemQuestao q : listaQuestoes){
            soma += q.situacao;
        }

        return soma;
    }

    @Override
    public String toString(){
        String msg = "";

        if(id>0) {
            if(respostaPrimaria!=respostasP.Indiferente) {
                msg = (id + 1) + " - " + perguntaPrincipal + "\n" + respostaPrimaria.toString() + "\n";
                if (respostaPrimaria == respostasP.Sim) {
                    msg += pergutaSecundária + "\n" + respostaSecundaria.toString() + "\n";
                }
                msg += "Classificação: " + situacao + "\n\n";
            }
        }else{
            msg = (id+1) + " - " + perguntaPrincipal + "\n" + tamanho + " m2" + "\n\n";
        }
        return msg;
    }

}
