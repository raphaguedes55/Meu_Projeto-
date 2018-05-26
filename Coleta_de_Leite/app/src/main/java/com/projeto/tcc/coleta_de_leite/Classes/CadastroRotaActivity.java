package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;
import com.projeto.tcc.coleta_de_leite.Utils.AdMob;

import java.util.Calendar;

public class CadastroRotaActivity extends AppCompatActivity {
    private EditText rotas,capacidade;
    private Button inicioColeta;
    private Spinner spinner;
    private TextClock textClock;
    String motoristaID;
    String Data;
    DatabaseReference databaseRotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrorotas);
        AdView  mAdView = (AdView)findViewById(R.id.adView);
        final AdMob adMob = new AdMob();
        adMob.mAdmob(getApplicationContext(),mAdView);
        listaId();
        metodoButoon();
        datadarota();
    }



    private boolean verificaCampos() {
        if (rotas.getText().toString().isEmpty()) {
            rotas.setError(getString(R.string.vazio));
            return false;

        }
        if (capacidade.getText().toString().isEmpty()) {

            capacidade.setError(getString(R.string.vazio));
            return false;}


        return true;
    }




    private void listaId(){
        Intent intent=getIntent();
        motoristaID=intent.getStringExtra(RotaActivity.motoristaId);
        System.out.println(Toast.makeText(getApplicationContext(), motoristaID, Toast.LENGTH_LONG));
        capacidade=(EditText)findViewById(R.id.edit_capacidade);
        textClock=(TextClock)findViewById(R.id.textClockRota);
        databaseRotas= FirebaseDatabase.getInstance().getReference("rotas").child(motoristaID);
        rotas = (EditText) findViewById(R.id.edit_Rota);
        inicioColeta=(Button)findViewById(R.id.saveRota);
        spinner=(Spinner)findViewById(R.id.spinnerRota);

    }
    private void metodoButoon(){
        inicioColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaCampos()){
                addRota();
                }

            }
        });
    }
    private void addRota(){
        String motoristaid=motoristaID;
        String mCapacidade=capacidade.getText().toString().trim();
        String hora=textClock.getText().toString().trim();
        String rota=rotas.getText().toString().trim();
        String tipoRota=spinner.getSelectedItem().toString();
            String id =databaseRotas.push().getKey();
            Rota rota1= new Rota(id,motoristaid,rota,tipoRota,Data,mCapacidade);
            databaseRotas.child(id).setValue(rota1);
           finish();

    }
      private void datadarota() {
        final Calendar c = Calendar.getInstance();
        String ano = "" + c.get(Calendar.YEAR);
        String mes=c.get(Calendar.MONTH)+1+"";
        if(mes.length()<2){
            mes="0"+mes;
        }
        String dia = "" + c.get(Calendar.DAY_OF_MONTH);
        if(dia.length()<2){
            dia="0"+dia;
        }
        Data=(dia +"/"+mes+"/"+ano);
    }
}
