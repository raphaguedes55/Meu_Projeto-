package com.projeto.tcc.coleta_de_leite.Dao;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Model.Rota;

/**
 * Created by raphael on 15/05/17.
 */

public class RotaDao {
   public boolean deleteRota(String idMotorista,String idRota) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("rotas").child(idMotorista).child(idRota);
        dR.removeValue();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("coletas").child(idRota);
        databaseReference.removeValue();

        return true;


    }
    public boolean updateRota(String id, String motoristaID,String name, String TipoRota, String Data,String carga) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("rotas").child(motoristaID).child(id);
        Rota rota = new Rota(id,motoristaID,name,TipoRota,Data,carga);
        dR.setValue(rota);
        return true;

    }

}
