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
    TextView temperatura;
    TextView data;
    TextView litros;
    TextView numeroAmostra;
    TextView obs;

    String aux;
    List<Coletas> coletasList;
    Button updateDelete;
    DatabaseReference databaseColetas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dadoscoleta);
        listadeid();
        metodoButton();
        Intent intent =getIntent();
        coletasList= new ArrayList<>();
        aux=intent.getStringExtra(ColetaActivity.ROTA_ID);
         Coletas coletas = (Coletas) getIntent().getSerializableExtra("coleta");


        Log.v("","Id Da Coleta foi "+aux);
        alizarol.setText(coletas.getAlizarol());
        produtor.setText(coletas.getNomeProdutor());
        matricula.setText(coletas.getMatProdutor());
        temperatura.setText(coletas.getTemperatura());
        litros.setText(coletas.getLitrosColeta());
        data.setText(coletas.getHoraColeta());
        numeroAmostra.setText(coletas.getAmostra());
        obs.setText(coletas.getObservaçao());
        updateDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coletas coletas = (Coletas) getIntent().getSerializableExtra("coleta");
                Intent intent= new Intent(getApplicationContext(),UpdateDeleteColetaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dados",coletas);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });





    }

    protected void metodoButton() {

    }

    protected void listadeid(){
        updateDelete=(Button)findViewById(R.id.button_updatedelete);
        alizarol=(TextView)findViewById(R.id.ver_alizarol);
        produtor=(TextView)findViewById(R.id.exib_prod);
        matricula=(TextView)findViewById(R.id.exib_mat);
        temperatura=(TextView)findViewById(R.id.ver_temperatura);
        litros=(TextView)findViewById(R.id.exib_Litros);
        data=(TextView)findViewById(R.id.exib_horario);
        obs=(TextView)findViewById(R.id.ver_obs);
        numeroAmostra=(TextView)findViewById(R.id.ver_amostra);



    }



}

