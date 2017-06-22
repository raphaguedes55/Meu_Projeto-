package com.projeto.tcc.coleta_de_leite_administrador.Class;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.tcc.coleta_de_leite_administrador.Adapter.MotoristaAdapter;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Motorista;
import com.projeto.tcc.coleta_de_leite_administrador.R;

import java.util.ArrayList;
import java.util.List;

public class MotoristaActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    ListView listViewMotoristas;
    List<Motorista> motoristasList;
    FloatingActionButton fab;
    DatabaseReference databaseMotoristas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motoristas);
        databaseMotoristas= FirebaseDatabase.getInstance().getReference("motoristas");
        listViewMotoristas=(ListView)findViewById(R.id.list_motorista) ;
        motoristasList =new ArrayList<>();
        metodosBotoes();


    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseMotoristas.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {



                motoristasList.clear();
                for(DataSnapshot motoristaSnapShot:dataSnapshot.getChildren()){

                    Motorista motorista =motoristaSnapShot.getValue(Motorista.class);
                    motoristasList.add(motorista);
                }
                MotoristaAdapter motoristaAdapter= new MotoristaAdapter(MotoristaActivity.this,motoristasList);
                listViewMotoristas.setAdapter(motoristaAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }




    private void metodosBotoes() {
        fab=(FloatingActionButton) findViewById(R.id.float_cadastro);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transicaoadc = new Intent(getApplicationContext(),NovoMotoristaActivity.class);
                startActivity(transicaoadc);

            }
        });}


}
