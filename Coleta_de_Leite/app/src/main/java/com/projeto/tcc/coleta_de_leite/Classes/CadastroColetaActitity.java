package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.R;

/**
 * Created by raphael on 30/03/17.
 */

public class CadastroColetaActitity extends AppCompatActivity {
    private EditText matricula;
    private TextClock horario;
    private EditText produtor;
    private EditText litros;
    private Button salva_coleta;
    private String rotaid;
    private Spinner spinner;
    private Spinner spinnerAlizarol;
    DatabaseReference databaseRotas;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrocoleta);
        findViewByIds();
        metodoBotoes();
    }

    private void metodoBotoes() {
        salva_coleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveColeta();
                finish();
            }

            
        });
    }

    private void saveColeta() {
        String nomeProdutor=produtor.getText().toString().trim();
        String litragem= litros.getText().toString().trim();
        String mat = matricula.getText().toString().trim();
        String tipoAlizarol=spinnerAlizarol.getSelectedItem().toString();
        String temperatura=spinner.getSelectedItem().toString();
        String hora=horario.getText().toString().trim();
        if(!TextUtils.isEmpty(nomeProdutor)){
            String id =databaseRotas.push().getKey();
            Coletas coletas= new Coletas(id,nomeProdutor,litragem,mat,hora,tipoAlizarol,temperatura);
            databaseRotas.child(id).setValue(coletas);
            finish();



        }else {
            produtor.setError(getString(R.string.rota_vazia));

        }

        }

    private void findViewByIds() {
        Intent intent=getIntent();
        rotaid=(intent.getStringExtra(ColetaActivity.rotaId));
        databaseRotas = FirebaseDatabase.getInstance().getReference("coletas").child(rotaid);
        spinnerAlizarol=(Spinner)findViewById(R.id.spinnerAlizarol);
        spinner=(Spinner)findViewById(R.id.spinnerTemperatura);
        matricula = (EditText) findViewById(R.id.edit_mat);
         horario= (TextClock) findViewById(R.id.textClock);
        produtor = (EditText) findViewById(R.id.edit_prod);
        litros = (EditText) findViewById(R.id.edit_litros);
        salva_coleta = (Button) findViewById(R.id.save);
    }
}
