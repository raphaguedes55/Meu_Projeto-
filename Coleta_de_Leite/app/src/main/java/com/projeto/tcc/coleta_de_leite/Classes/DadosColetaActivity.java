package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael on 13/05/17.
 */

public class DadosColetaActivity extends AppCompatActivity {
    TextView produtor;
    TextView matricula;
    TextView alizarol;
    TextView qualidade;
    TextView data;
    TextView litros;
    String aux;
    List<Coletas> coletasList;
    DatabaseReference databaseColetas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dadoscoleta);
        listadeid();
        Intent intent =getIntent();
        coletasList= new ArrayList<>();
        aux=intent.getStringExtra(ColetaActivity.ROTA_ID);
        Coletas coletas = (Coletas) getIntent().getSerializableExtra("coleta");
        Toast.makeText(getApplicationContext(), " Coleta " + coletas.getNomeProdutor(), Toast.LENGTH_LONG).show();
        Log.v("","Id Da Coleta foi "+aux);
        produtor.setText(coletas.getNomeProdutor());
        matricula.setText(coletas.getMatProdutor());
        qualidade.setText(coletas.getQualidadeLeite());
        litros.setText(coletas.getLitrosColeta());
        data.setText(coletas.getHoraColeta());





    }
    protected void listadeid(){
        produtor=(TextView)findViewById(R.id.exib_prod);
        matricula=(TextView)findViewById(R.id.exib_mat);
        qualidade=(TextView)findViewById(R.id.exib_qualidade);
        litros=(TextView)findViewById(R.id.exib_Litros);
        data=(TextView)findViewById(R.id.exib_horario);
    }



}

