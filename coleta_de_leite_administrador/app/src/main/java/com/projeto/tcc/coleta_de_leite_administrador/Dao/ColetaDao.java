package com.projeto.tcc.coleta_de_leite_administrador.Dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Coletas;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Coletas;

/**
 * Created by raphael on 15/05/17.
 */

public class ColetaDao {
    public boolean deleteColeta(String idRota,String idColeta){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("coletas").child(idRota).child(idColeta);
        dR.removeValue();
        return true;

    }
    public boolean updateColeta(String id, String rotaid,String nomeProdutor, String litragem, String mat,String hora,String tipoAlizarol,String temperatura,String amostra,String retificado,String obs,String motivo,String imagem) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("coletas").child(rotaid).child(id);
        Coletas coletas = new Coletas(id,rotaid,nomeProdutor,litragem,mat,hora,tipoAlizarol,temperatura,amostra,retificado,obs,imagem,motivo);
        dR.setValue(coletas);
        return true;
}
}
