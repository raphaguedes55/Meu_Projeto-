package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.tcc.coleta_de_leite.Adapter.RotaAdapter;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

import java.util.ArrayList;
import java.util.List;

public class RotaActivity extends AppCompatActivity {
    public static final String ROTA_ID="com.projeto.tcc.coleta_de_leite.rotaid";
    public static final String motoristaId="com.projeto.tcc.coleta_de_leite.motoristaId";
    ListView listViewRota;
    DatabaseReference databaseRotas;
    List<Rota> rotaList;
    String idmotorista;

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listarIds();
        metodosBotoes();





    }
    @Override
    protected void onStart() {
        super.onStart();
        listadeRotas();

    }


    private void metodosBotoes() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transicaoadc = new Intent(RotaActivity.this,CadastroRotaActivity.class);
                transicaoadc.putExtra(motoristaId,idmotorista);
                startActivity(transicaoadc);

            }
        });
    }



    private void listarIds() {
        databaseRotas= FirebaseDatabase.getInstance().getReference("rotas").child(idmotorista);;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        idmotorista=intent.getStringExtra(LoginActivity.motoristaId);
        listViewRota=(ListView) findViewById(R.id.list_rota);
        rotaList= new ArrayList<>();
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }



    private void listadeRotas() {

        databaseRotas.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {



                rotaList.clear();
                for(DataSnapshot rotaSnapShot:dataSnapshot.getChildren()){

                    Rota rota =rotaSnapShot.getValue(Rota.class);
                    rotaList.add(rota);
                }
                RotaAdapter rotaAdapter = new RotaAdapter(RotaActivity.this,rotaList);
                listViewRota.setAdapter(rotaAdapter);
                listViewRota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //getting the selected artist
                        Rota rota = rotaList.get(i);

                        //creating an intent
                        Intent intent = new Intent(getApplicationContext(),ColetaActivity.class);
                        intent.putExtra(ROTA_ID,rota.getRotaId());
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }
}

