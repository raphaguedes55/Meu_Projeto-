package com.example.notebookdell.controlenutricional.model;

import java.io.Serializable;

public class Receita implements Serializable {
    private String id ;
    private String nome;
    private String ingredientes;
    private String preparo;
    private String tempo;
    private String categoria;
    private String data;
    private Imagem imagem1 = new Imagem();
    private Imagem imagem2 = new Imagem();
    private Imagem imagem3 = new Imagem();

    public Receita(String id , String nome, String ingredientes , String preparo, String tempo , String data, String categoria){
        this.id=id;
        this.nome=nome;
        this.ingredientes=ingredientes;
        this.preparo=preparo;
        this.tempo=tempo;
        this.data=data;
        this.categoria=categoria;
    }
    public  Receita(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPreparo() {
        return preparo;
    }

    public void setPreparo(String preparo) {
        this.preparo = preparo;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public Imagem getImagem1() {
        return imagem1;
    }

    public void setImagem1(Imagem imagem1) {
        this.imagem1 = imagem1;
    }

    public Imagem getImagem2() {
        return imagem2;
    }

    public void setImagem2(Imagem imagem2) {
        this.imagem2 = imagem2;
    }

    public Imagem getImagem3() {
        return imagem3;
    }

    public void setImagem3(Imagem imagem3) {
        this.imagem3 = imagem3;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
