package com.raissa.analiseapp;

/**
 * Created by primelan on 9/23/15.
 */
public class ItemCadastro {
    String id;
    String foto;
    String txt;
    String tabela;
    String nome;
    String matricula;



    public ItemCadastro(String id,String nome, String matricula, String foto, String txt, String tabela) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.foto = foto;
        this.txt = txt;
        this.tabela = tabela;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }
}
