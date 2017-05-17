package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.tcc.coleta_de_leite.Adapter.ColetaAdapter;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

import java.util.ArrayList;
import java.util.List;

public class ColetaActivity extends AppCompatActivity {
    public static final String rotaId="com.projeto.tcc.coleta_de_leite.rotaid";
    public static final String ROTA_ID="com.projeto.tcc.coleta_de_leite.rotaid";
   int coletaId=0;
    public String rotaName;
    ListView listViewColetas;
    DatabaseReference databaseRotas;
    List<Coletas> coletasList;
    String idDaRota;
  FirebaseAuth auth;
 int positon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coleta);
        listaId();
        metodobotoes();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desejos,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actio_sair) {
            auth.signOut();
            Intent intent=new Intent(ColetaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                listViewColetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        positon=i;
                        Coletas coletas = coletasList.get(i);
                        Intent intent = new Intent(getApplicationContext(),DadosColetaActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("coleta", coletas);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void listaId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Lista De Coletas");
        coletasList = new ArrayList<>();
        Intent intent =getIntent();
        idDaRota=intent.getStringExtra(RotaActivity.ROTA_ID);
        databaseRotas = FirebaseDatabase.getInstance().getReference("coletas").child(idDaRota);
        listViewColetas= (ListView) findViewById(R.id.list_coleta);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
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
