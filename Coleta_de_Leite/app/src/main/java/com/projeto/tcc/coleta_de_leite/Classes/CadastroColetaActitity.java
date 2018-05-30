package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.R;

import java.util.Calendar;

import static com.projeto.tcc.coleta_de_leite.Classes.ColetaActivity.rotaId;

/**
 * Created by raphael on 30/03/17.
 */

public class CadastroColetaActitity extends AppCompatActivity {
    private EditText medida;
    private EditText produtor;
    private EditText litros;
    private EditText numeroAmostra;
    private Button salva_coleta;
    String idDaRota;
    String horario;
    private Spinner spinner;
    private Spinner spinnerAlizarol;
    DatabaseReference databaseRotas;
    AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrocoleta);
        MobileAds.initialize(this, "ca-app-pub-7740037973360371/4869779032");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        findViewByIds();
        final Calendar c = Calendar.getInstance();
        String hora = "" + c.get(Calendar.HOUR_OF_DAY);
        String minute=""+c.get(Calendar.MINUTE);
        horario=(hora+":"+minute);
        databaseRotas = FirebaseDatabase.getInstance().getReference("coletas").child(idDaRota);
        metodoBotoes();

    }
    private boolean verificaCampos() {
        if (medida.getText().toString().isEmpty()) {
            medida.setError(getString(R.string.vazio));
            return false;

        }
        if (produtor.getText().toString().isEmpty()) {

            produtor.setError(getString(R.string.vazio));
            return false;}
        if (numeroAmostra.getText().toString().isEmpty()) {

            numeroAmostra.setError(getString(R.string.vazio));
            return false;}

        if (litros.getText().toString().isEmpty()) {

            litros.setError(getString(R.string.vazio));
            return false;}



        return true;
    }

    private void metodoBotoes() {
        salva_coleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampos()){
                saveColeta();
                finish();}
            }

            
        });
    }


    private void saveColeta() {
        HoradaColeta();
        String retificado=" ";
        String obs="";
        String nomeProdutor=produtor.getText().toString().trim();
        String litragem= litros.getText().toString().trim();
        String medidas= medida.getText().toString().trim();
        String tipoAlizarol=spinnerAlizarol.getSelectedItem().toString();
        String temperatura=spinner.getSelectedItem().toString();
        String amostra=numeroAmostra.getText().toString().trim();
            String id =databaseRotas.push().getKey();
            Coletas coletas= new Coletas(id,idDaRota,nomeProdutor,litragem,medidas,horario,tipoAlizarol,temperatura,amostra,retificado,obs,"","");
            databaseRotas.child(id).setValue(coletas);
            finish();
    }

    private void findViewByIds() {
        Intent intent=getIntent();
        idDaRota=(intent.getStringExtra(rotaId));
        numeroAmostra=(EditText)findViewById(R.id.edit_amostra);
        spinnerAlizarol=(Spinner)findViewById(R.id.spinnerAlizarol);
        spinner=(Spinner)findViewById(R.id.spinnerTemperatura);
        medida = (EditText) findViewById(R.id.edit_mat);
        produtor = (EditText) findViewById(R.id.edit_prod);
        litros = (EditText) findViewById(R.id.edit_litros);
        salva_coleta = (Button) findViewById(R.id.save);
    }
    private void HoradaColeta() {
        final String Minuto;
        final Calendar c = Calendar.getInstance();
        String hora = "" + c.get(Calendar.HOUR_OF_DAY);
        int minuto=c.get(Calendar.MINUTE);
        if(minuto<10){
             Minuto="0"+minuto;
        }else {
             Minuto=""+minuto;
        }
        horario=(hora+":"+Minuto);
    }

    }

