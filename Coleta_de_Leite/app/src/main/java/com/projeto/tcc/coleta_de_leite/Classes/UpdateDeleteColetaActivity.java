package com.projeto.tcc.coleta_de_leite.Classes;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Dao.ColetaDao;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

/**
 * Created by raphael on 19/05/17.
 */

public class UpdateDeleteColetaActivity extends AppCompatActivity{
    private EditText matricula;
    private TextClock horario;
    private EditText produtor;
    private EditText litros;
    private EditText obs;
    private TextView amostra;
    private Button atualizaColeta;
    private Button deletaColeta;
    private String rotaid;
    private Spinner spinner;
    private Spinner spinnerAlizarol;
    DatabaseReference databaseRotas;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizacoleta);
        final Coletas coletas = (Coletas) getIntent().getSerializableExtra("dados");
        findViewByIds();

        matricula.setText(coletas.getMatProdutor());
        horario.setText(coletas.getHoraColeta());
        produtor.setText(coletas.getNomeProdutor());
        litros.setText(coletas.getLitrosColeta());
        amostra.setText(coletas.getAmostra());
        String retifica="Registro Retificado";
        final String idRota= coletas.getIdRota();
        final String idColeta= coletas.getIdColeta();
        final  ColetaDao coletaDao=new ColetaDao();;


        deletaColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coletaDao.deleteColeta(idRota,idColeta);
                finish();
            }
        });
        atualizaColeta.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(verificaCampos()) {
                    String sobs = obs.getText().toString().trim();
                    String id = coletas.getIdColeta();
                    String rotaId = coletas.getIdRota();
                    String nomeProdutor = produtor.getText().toString().trim();
                    String litragem = litros.getText().toString().trim();
                    String mat = matricula.getText().toString().trim();
                    String hora = coletas.getHoraColeta();
                    String alizarol = spinnerAlizarol.getSelectedItem().toString();
                    String temperatura = spinner.getSelectedItem().toString();
                    String namostra = amostra.getText().toString().trim();
                    String retificado = "Registro Retificado";


                    coletaDao.updateColeta(id, rotaId, nomeProdutor, litragem, mat, hora, alizarol, temperatura, namostra, retificado, sobs);
                    finish();
                } }
        });




    }
    private boolean verificaCampos() {
        if (matricula.getText().toString().isEmpty()) {
            matricula.setError(getString(R.string.vazio));
            return false;

        }
        if (produtor.getText().toString().isEmpty()) {

            produtor.setError(getString(R.string.vazio));
            return false;}
        if (amostra.getText().toString().isEmpty()) {

            amostra.setError(getString(R.string.vazio));
            return false;}
        if (obs.getText().toString().isEmpty()) {

            obs.setError(getString(R.string.vazio));
            return false;}


        if (litros.getText().toString().isEmpty()) {

            litros.setError(getString(R.string.vazio));
            return false;}



        return true;
    }




    private void findViewByIds() {
        obs=(EditText)findViewById(R.id.edit_update_obs);
        deletaColeta=(Button)findViewById(R.id.button_deletar_coleta);
        atualizaColeta=(Button)findViewById(R.id.button_atualiza_coleta);
        amostra=(EditText)findViewById(R.id.edit_update_amostra);
        matricula=(EditText)findViewById(R.id.edit_update_matricula);
        horario=(TextClock)findViewById(R.id.textClock);
        produtor=(EditText)findViewById(R.id.edit_update_prod);
        litros=(EditText)findViewById(R.id.edit_update_litros);
        spinnerAlizarol=(Spinner)findViewById(R.id.spinner_update_Alizarol);
        spinner=(Spinner)findViewById(R.id.spinner_update_Temperatura);
    }





}


