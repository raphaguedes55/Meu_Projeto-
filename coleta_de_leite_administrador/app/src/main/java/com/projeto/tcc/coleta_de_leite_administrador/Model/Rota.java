package com.projeto.tcc.coleta_de_leite_administrador.Model;

import java.io.Serializable;

/**
 * Created by raphael on 29/03/17.
 */

public class Rota implements Serializable {

    String motoristaid;
    String rotaId;
    String horaRota;
    String nomeRota;
    String tipoRota;
    String capacidade;

    public Rota(){}

    public Rota(String rotaId,String motoristaid, String nomeRota, String tipoRota,String horaRota,String capacidade) {
        this.rotaId = rotaId;
        this.capacidade=capacidade;
        this.nomeRota = nomeRota;
        this.tipoRota = tipoRota;
        this.horaRota =horaRota;
        this.motoristaid=motoristaid;
    }

    public String getMotoristaid(){return motoristaid;};
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

