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

import static com.projeto.tcc.coleta_de_leite.Classes.ColetaActivity.rotaId;

/**
 * Created by raphael on 30/03/17.
 */

public class CadastroColetaActitity extends AppCompatActivity {
    private EditText matricula;
    private TextClock horario;
    private EditText produtor;
    private EditText litros;
    private EditText numeroAmostra;
    private Button salva_coleta;
    private Button ocorrencia_coleta;
   String rotaid;
    String idDaRota;
    private Spinner spinner;
    private Spinner spinnerAlizarol;
    DatabaseReference databaseRotas;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrocoleta);
        findViewByIds();
        databaseRotas = FirebaseDatabase.getInstance().getReference("coletas").child(idDaRota);
        metodoBotoes();
    }
    private boolean verificaCampos() {
        if (matricula.getText().toString().isEmpty()) {
            matricula.setError(getString(R.string.vazio));
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
        ocorrencia_coleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transicaoadc = new Intent(CadastroColetaActitity.this,OcorrenciaActivity.class);
                transicaoadc.putExtra(rotaid,idDaRota);
                startActivity(transicaoadc);
            }
        });
    }


    private void saveColeta() {
        String retificado=" ";
        String obs="";
        String nomeProdutor=produtor.getText().toString().trim();
        String litragem= litros.getText().toString().trim();
        String mat = matricula.getText().toString().trim();
        String tipoAlizarol=spinnerAlizarol.getSelectedItem().toString();
        String temperatura=spinner.getSelectedItem().toString();
        String hora=horario.getText().toString().trim();
        String amostra=numeroAmostra.getText().toString().trim();
            String id =databaseRotas.push().getKey();
            Coletas coletas= new Coletas(id,idDaRota,nomeProdutor,litragem,mat,hora,tipoAlizarol,temperatura,amostra,retificado,obs,"","");
            databaseRotas.child(id).setValue(coletas);
            finish();
    }

    private void findViewByIds() {
        Intent intent=getIntent();
        idDaRota=(intent.getStringExtra(rotaId));
        numeroAmostra=(EditText)findViewById(R.id.edit_amostra);
        spinnerAlizarol=(Spinner)findViewById(R.id.spinnerAlizarol);
        spinner=(Spinner)findViewById(R.id.spinnerTemperatura);
        matricula = (EditText) findViewById(R.id.edit_mat);
         horario= (TextClock) findViewById(R.id.textClock);
        produtor = (EditText) findViewById(R.id.edit_prod);
        litros = (EditText) findViewById(R.id.edit_litros);
        salva_coleta = (Button) findViewById(R.id.save);
        ocorrencia_coleta=(Button)findViewById(R.id.ocorrencia);
    }
}
