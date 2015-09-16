package com.raissa.analiseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by primelan on 9/3/15.
 */

public class ItemQuestao implements Serializable{
    enum respostasP {Sim, Nao;

        @Override
        public String toString() {
            if(this==Sim) return "Sim";
            else return "Não";
        }
    }
    enum respostasS {Pouco,Muito,Indiferente;

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
            msg = (id + 1) + " - " + perguntaPrincipal + "\n" + respostaPrimaria.toString() + "\n" ;
            if(respostaPrimaria==respostasP.Sim) {
                msg +=  pergutaSecundária + "\n" + respostaSecundaria.toString() + "\n";
            }
            msg += "Classificação: " + situacao + "\n\n";
        }else{
            msg = (id+1) + " - " + perguntaPrincipal + "\n" + tamanho + " m2" + "\n\n";
        }
        return msg;
    }

}
