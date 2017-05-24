package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

import static com.projeto.tcc.coleta_de_leite.R.id.update;
import static com.projeto.tcc.coleta_de_leite.R.id.update_spinnerRota;

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
        Intent intent=getIntent();
      motoristaID=intent.getStringExtra(RotaActivity.motoristaId);

        Toast.makeText(getApplicationContext(),motoristaID,Toast.LENGTH_LONG).show();


        Rota rota= (Rota) getIntent().getSerializableExtra("rota");
        update_rota.setText(rota.getNomeRota());
        update_capacidade.setText(rota.getCapacidade());
        idRota=rota.getRotaId();
        hora=rota.getHoraRota();
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRota(idRota);

            }
        });
        update_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = update_rota.getText().toString().trim();
                String carga= update_capacidade.getText().toString().trim();
                String tipo = spinner_rota.getSelectedItem().toString();


                updateRota(idRota, name,carga,tipo,hora);
                finish();

            }
        });}



    private void findViewByIds() {
        spinner_rota=(Spinner)findViewById(R.id.update_spinnerRota);
        update_button=(Button)findViewById(R.id.update_Rota);
        delete_button=(Button) findViewById(R.id.delete_rota);
        update_rota=(EditText) findViewById(R.id.edit_update_Rota);
        update_capacidade=(EditText)findViewById(R.id.edit_update_capacidade);

    }
    private boolean deleteRota(String id) {
        //getting the specified artist reference
        DatabaseReference dR =  FirebaseDatabase.getInstance().getReference("motoristas").child(id);

        //removing artist
        dR.removeValue();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("coletas").child(id);


        databaseReference.removeValue();
        Toast.makeText(getApplicationContext(), "Artist Deleted", Toast.LENGTH_LONG).show();
    return true;


    }
    private boolean updateRota(String id, String name, String genre,String TipoRota, String Data) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("rotas").child(id);
        Rota rota = new Rota(id,name, genre,TipoRota,Data);
        dR.setValue(rota);
        return true;

    }
    }
