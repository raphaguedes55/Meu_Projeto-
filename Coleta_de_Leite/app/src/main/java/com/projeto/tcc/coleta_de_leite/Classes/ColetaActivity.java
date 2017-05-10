package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.tcc.coleta_de_leite.Adapter.ColetaAdapter;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.R;

import java.util.ArrayList;
import java.util.List;

public class ColetaActivity extends AppCompatActivity {
    public static final String rotaId="com.projeto.tcc.coleta_de_leite.rotaid";
    public String rotaName;
    ListView listViewColetas;
    DatabaseReference databaseRotas;
    List<Coletas> coletasList;
    String idDaRota;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coleta);
        listaId();
        metodobotoes();


    }



    @Override
    protected void onStart() {
        super.onStart();
        databaseRotas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               coletasList.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Coletas mColeta =postSnapshot.getValue(Coletas.class);
                    coletasList.add(mColeta);
                }
                ColetaAdapter coletaAdapter= new ColetaAdapter(ColetaActivity.this,coletasList);
                listViewColetas.setAdapter(coletaAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void listaId() {
        coletasList = new ArrayList<>();
        Intent intent =getIntent();
        idDaRota=intent.getStringExtra(RotaActivity.ROTA_ID);
        databaseRotas = FirebaseDatabase.getInstance().getReference("coletas").child(idDaRota);
        listViewColetas= (ListView) findViewById(R.id.list_coleta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void metodobotoes() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent transicaoadc = new Intent(ColetaActivity.this,CadastroColetaActitity.class);
                transicaoadc.putExtra(rotaId,idDaRota);
                startActivity(transicaoadc);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}
