package com.projeto.tcc.coleta_de_leite.Model;

import java.io.Serializable;

/**
 * Created by raphael on 30/03/17.
 */

public class Coletas implements Serializable{
    private String horaColeta;
    private String idColeta;
    private String nomeProdutor;
    private String litrosColeta;
    private String matProdutor;
    private String temperatura;
    private String alizarol;
    //private int rating;
    public Coletas (){}

    public Coletas(String idColeta,String nomeProdutor,String litrosColeta,String matProdutor,String horaColeta,String Alizarol,String temperatura){

        this.matProdutor=matProdutor;
        this.alizarol=Alizarol;
        this.temperatura=temperatura;
        this.idColeta=idColeta;
        this.nomeProdutor=nomeProdutor;
        this.litrosColeta=litrosColeta;
        this.horaColeta=horaColeta;

    }
    public String getAlizarol(){return alizarol;}
    public String getHoraColeta(){return horaColeta;}
    public String getMatProdutor(){return matProdutor;};
    public String getTemperatura(){return temperatura;};
    public String getIdColeta() {
        return idColeta;
    }

    public String getNomeProdutor() {
        return nomeProdutor;
    }

    public String getLitrosColeta() {
        return litrosColeta;
    }



}
