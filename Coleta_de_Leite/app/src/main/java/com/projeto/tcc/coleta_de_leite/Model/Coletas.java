package com.projeto.tcc.coleta_de_leite.Model;

/**
 * Created by raphael on 30/03/17.
 */

public class Coletas {
    private String horaColeta;
    private String idColeta;
    private String nomeProdutor;
    private String litrosColeta;
    private String matProdutor;
    private String qualidadeLeite;
    private String alizarol;
    //private int rating;
    public Coletas (){}

    public Coletas(String idColeta,String nomeProdutor,String litrosColeta,String matProdutor,String qualidadeLeite,String horaColeta,String Alizarol){
        this.matProdutor=matProdutor;
        this.alizarol=Alizarol;
        this.qualidadeLeite=qualidadeLeite;
        this.idColeta=idColeta;
        this.nomeProdutor=nomeProdutor;
        this.litrosColeta=litrosColeta;
        this.horaColeta=horaColeta;

    }
    public String getAlizarol(){return alizarol;}
    public String getHoraColeta(){return horaColeta;}
    public String getMatProdutor(){return matProdutor;};
    public String getQualidadeLeite(){return qualidadeLeite;};
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
