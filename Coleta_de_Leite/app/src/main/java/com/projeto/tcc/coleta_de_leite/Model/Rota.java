package com.projeto.tcc.coleta_de_leite.Model;

/**
 * Created by raphael on 29/03/17.
 */

public class Rota {


    String rotaId;
    String horaRota;
    String nomeRota;
    String tipoRota;
    String capacidade;

    public Rota(){}

    public Rota(String rotaId, String nomeRota, String tipoRota,String horaRota,String capacidade) {
        this.rotaId = rotaId;
        this.capacidade=capacidade;
        this.nomeRota = nomeRota;
        this.tipoRota = tipoRota;
        this.horaRota =horaRota;
    }
    public String getCapacidade(){return capacidade;};
    public String getHoraRota(){return horaRota;}

    public String getRotaId() {
        return rotaId;
    }

    public String getNomeRota() {
        return nomeRota;
    }

    public String getTipoRota() {
        return tipoRota;
    }

}

