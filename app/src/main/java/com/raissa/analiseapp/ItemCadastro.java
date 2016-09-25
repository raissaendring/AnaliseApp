package com.raissa.analiseapp;

/**
 * Created by primelan on 9/23/15.
 */
public class ItemCadastro {
    String id;
    String foto;
    String nome;
    String matricula;
    double latitude, longitude;
    int valor;
    String referencia;


    public ItemCadastro(String id, String foto, String nome, String matricula, double latitude, double longitude, int valor, String referencia) {
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.matricula = matricula;
        this.latitude = latitude;
        this.longitude = longitude;
        this.valor = valor;
        this.referencia = referencia;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
