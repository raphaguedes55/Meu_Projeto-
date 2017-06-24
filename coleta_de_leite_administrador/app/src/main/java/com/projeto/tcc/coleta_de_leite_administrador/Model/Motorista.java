package com.projeto.tcc.coleta_de_leite_administrador.Model;

/**
 * Created by raphael on 21/06/17.
 */

public class Motorista {
    private String Idmotorista;




    private String identificador;
    private String emailmotorista;
    private String nome;
    private String senha;

    public Motorista(){
        //construtor
    }


    public Motorista(String Idmotorista, String emailmotorista, String nome, String Senha,String identificador) {
        this.Idmotorista= Idmotorista;
        this.emailmotorista=emailmotorista;
        this.nome=nome;
        this.senha=Senha;
        this.identificador=identificador;
    }
    public String getIdentificador() {
        return identificador;
    }

    public String getIdmotorista() {
        return Idmotorista;
    }

    public String getEmailmotorista() {
        return emailmotorista;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }




    }


