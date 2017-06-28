package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Dao.ColetaDao;
import com.projeto.tcc.coleta_de_leite.Dao.RotaDao;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

/**
 * Created by raphael on 23/05/17.
 */

public class UpdateDeleteRotaActivity extends AppCompatActivity {
     String idRota;
    String hora;
    Spinner spinner_rota;
    EditText update_rota,update_capacidade;
    Button update_button,delete_button;
    DatabaseReference databaseRotas;
    String motoristaID;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizarota);
        findViewByIds();



        Rota rota= (Rota) getIntent().getSerializableExtra("rota");

        update_rota.setText(rota.getNomeRota());
        update_capacidade.setText(rota.getCapacidade());
        idRota=rota.getRotaId();
        motoristaID=rota.getMotoristaid();
        hora=rota.getHoraRota();
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DeleteDialog();

            }
        });
        update_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateDialog();


            }
        });}
    private boolean verificaCampos() {
        if (update_rota.getText().toString().isEmpty()) {
            update_rota.setError(getString(R.string.vazio));
            return false;

        }
        if (update_capacidade.getText().toString().isEmpty()) {

            update_capacidade.setError(getString(R.string.vazio));
            return false;}


        return true;
    }


    private void findViewByIds() {
        spinner_rota=(Spinner)findViewById(R.id.update_spinnerRota);
        update_button=(Button)findViewById(R.id.update_Rota);
        delete_button=(Button) findViewById(R.id.delete_rota);
        update_rota=(EditText) findViewById(R.id.edit_update_Rota);
        update_capacidade=(EditText)findViewById(R.id.edit_update_capacidade);

    }

    private void DeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_delete, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAceitar = (Button) dialogView.findViewById(R.id.button_aceitar);
        final Button buttonCancelar = (Button) dialogView.findViewById(R.id.button_cancelar);

        dialogBuilder.setTitle("ATENCAO");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RotaDao rotaDao=new RotaDao();
               rotaDao.deleteRota(motoristaID,idRota);
                finish();
            }
        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }

    private void UpdateDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAceitar = (Button) dialogView.findViewById(R.id.button_aceitar);
        final Button buttonDeletar = (Button) dialogView.findViewById(R.id.button_cancelar);

        dialogBuilder.setTitle("ATENCAO");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaCampos()){
                String name = update_rota.getText().toString().trim();
                String carga= update_capacidade.getText().toString().trim();
                String tipo = spinner_rota.getSelectedItem().toString();


                    final RotaDao rotaDao=new RotaDao();
                rotaDao.updateRota(idRota,motoristaID, name,tipo,hora,carga);
                finish();
                }


            }
        });
        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }


}
