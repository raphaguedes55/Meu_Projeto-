package com.projeto.tcc.coleta_de_leite_administrador.Model;

/**
 * Created by raphael on 05/07/17.
 */

public class Administrador {

    public String idAdm;
    public String matricula;


    public Administrador() {
    }

    public Administrador(String idAdm, String matricula) {
        this.idAdm = idAdm;
        this.idAdm = matricula;

    }
    public String getIdAdm() {
        return idAdm;
    }
    public String getMatricula() {
        return matricula;
    }


}