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
    private String medida;
    private String temperatura;
    private String alizarol;
    private String amostra;
    private String retificado;
    private String observaçao;
    private  String motivo;
    private String imagem;
    private String compartimento;


    //private int rating;
    public Coletas (){}


    public Coletas(String idColeta,String idRota,String nomeProdutor,String litrosColeta,
                   String medida,String horaColeta,String Alizarol,String temperatura,
                   String amostra,String retifica,String observaçao,String motivo,String imagem,String compartimento){
        this.idColeta=idColeta;
        this.idRota=idRota;
        this.medida =medida;
        this.alizarol=Alizarol;
        this.temperatura=temperatura;
        this.nomeProdutor=nomeProdutor;
        this.litrosColeta=litrosColeta;
        this.horaColeta=horaColeta;
        this.amostra=amostra;
        this.retificado=retifica;
        this.observaçao=observaçao;
        this.imagem=imagem;
        this.motivo=motivo;
        this.compartimento=compartimento;


    }




    public String getIdRota(){return idRota;}
    public String getAlizarol(){return alizarol;}
    public String getHoraColeta(){return horaColeta;}
    public String getMedida(){return medida;};
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
    public String getCompartimento(){return compartimento;}
    public  String getImagem(){return imagem;}
    public String getMotivo(){return  motivo;}


}
