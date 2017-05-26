package com.projeto.tcc.coleta_de_leite.Model;

import java.io.Serializable;

/**
 * Created by raphael on 30/03/17.
 */

public class Coletas implements Serializable{

    private String idColeta;
    private String idRota;
    private String horaColeta;
    private String nomeProdutor;
    private String litrosColeta;
    private String matProdutor;
    private String temperatura;
    private String alizarol;
    private String amostra;
    private String retificado;
    private String observaçao;


    //private int rating;
    public Coletas (){}

    public Coletas(String idColeta,String idRota,String nomeProdutor,String litrosColeta,
                   String matProdutor,String horaColeta,String Alizarol,String temperatura,
                   String amostra,String retifica,String observaçao){
        this.idColeta=idColeta;
        this.idRota=idRota;
        this.matProdutor=matProdutor;
        this.alizarol=Alizarol;
        this.temperatura=temperatura;
        this.nomeProdutor=nomeProdutor;
        this.litrosColeta=litrosColeta;
        this.horaColeta=horaColeta;
        this.amostra=amostra;
        this.retificado=retifica;
        this.observaçao=observaçao;


    }




    public String getIdRota(){return idRota;}
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
    public String getAmostra(){return amostra;}
    public String getRetificado(){return retificado;}
    public String getObservaçao(){return observaçao;}


}
