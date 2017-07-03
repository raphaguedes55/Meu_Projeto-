package com.projeto.tcc.coleta_de_leite_administrador.Model;

import java.io.Serializable;

/**
 * Created by raphael on 21/06/17.
 */

public class Motorista implements Serializable {
    private String motoristaId;
    private String matricula;
    private String emailmotorista;
    private String nome;
    private String senha;

    public Motorista(){
        //construtor
    }


    public Motorista( String motoristaId,String matricula,String emailmotorista, String nome, String Senha) {
        this.motoristaId= motoristaId;
        this.emailmotorista=emailmotorista;
        this.nome=nome;
        this.senha=Senha;
        this.matricula=matricula;
    }
    public String getMatricula() {
        return matricula;
    }

    public String getmotoristaId() {
        return motoristaId;
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


