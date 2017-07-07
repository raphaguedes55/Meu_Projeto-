package com.projeto.tcc.coleta_de_leite_administrador.Dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Motorista;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Rota;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Rota;

/**
 * Created by raphael on 15/05/17.
 */

public class MotoristaDao {

    public boolean UpdateMotorista(String motoristaId, String matricula, String emailmotorista, String nome, String senha) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("motoristas").child(motoristaId);
        Motorista motorista = new Motorista(matricula,motoristaId,emailmotorista,nome,senha);
        dR.setValue(motorista);
        return true;

    }

}
