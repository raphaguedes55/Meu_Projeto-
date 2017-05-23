package com.projeto.tcc.coleta_de_leite.Classes;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;

import com.google.firebase.database.DatabaseReference;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.R;

/**
 * Created by raphael on 19/05/17.
 */

public class UpdateDeleteColetaActivity extends AppCompatActivity{
    private EditText matricula;
    private TextClock horario;
    private EditText produtor;
    private EditText litros;
    private Button atualizaColeta;
    private Button deletaRota;
    private String rotaid;
    private Spinner spinner;
    private Spinner spinnerAlizarol;
    DatabaseReference databaseRotas;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizacoleta);
        findViewByIds();
        metodoBotoes();
        Coletas coletas = (Coletas) getIntent().getSerializableExtra("dados");
        matricula.setText(coletas.getMatProdutor());
        horario.setText(coletas.getHoraColeta());
        produtor.setText(coletas.getNomeProdutor());
        litros.setText(coletas.getLitrosColeta());



    }



    private void findViewByIds() {
        matricula=(EditText)findViewById(R.id.edit_update_matricula);
        horario=(TextClock)findViewById(R.id.textClock);
        produtor=(EditText)findViewById(R.id.edit_update_prod);
        litros=(EditText)findViewById(R.id.edit_update_litros);
        spinnerAlizarol=(Spinner)findViewById(R.id.spinner_update_Alizarol);
        spinner=(Spinner)findViewById(R.id.spinner_update_Temperatura);
    }
    private void metodoBotoes() {
    }
}

