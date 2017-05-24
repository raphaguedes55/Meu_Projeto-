package com.projeto.tcc.coleta_de_leite.Classes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ProgressDialog progressDialog;
    public String rotaName;
    ListView listViewColetas;
    DatabaseReference databaseRotas;
    List<Coletas> coletasList;
    String idDaRota;
    int capacidade;
    TextView text_capacidade;
    TextView text_produtor;
    TextView text_litros;
    int Nprodutor;
    int TotalColeta;
    FirebaseAuth auth;
    String aux;
    int positon;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frame_dados);

                    frameLayout.setVisibility(View.GONE);
                    return  true;


                case R.id.navigation_dashboard:
                    Intent transicaoadc = new Intent(ColetaActivity.this,CadastroColetaActitity.class);
                    transicaoadc.putExtra(rotaId,idDaRota);
                    startActivity(transicaoadc);
                    return true;

                case R.id.navigation_notifications:

                    FrameLayout frameLayout1 = (FrameLayout)findViewById(R.id.frame_dados);
                    frameLayout1.setVisibility(View.VISIBLE);
                    return true;

            }
            return false;
        }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coleta);


        listaId();
        aux =(String) getIntent().getSerializableExtra("capacidade");
        Toast.makeText(getApplicationContext(),idDaRota,Toast.LENGTH_LONG).show();
        databaseRotas = FirebaseDatabase.getInstance().getReference("coletas").child(idDaRota);


        capacidade = Integer.parseInt(aux);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando Coleta...");
        progressDialog.show();


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
                    int numero = Integer.parseInt(mColeta.getLitrosColeta());
                    String alizarol=mColeta.getAlizarol();




                        TotalColeta = TotalColeta + numero;
                        Nprodutor = Nprodutor + 1;


                    coletasList.add(mColeta);

                }
                int aux1 = capacidade - TotalColeta;

                text_capacidade.setText(""+ aux1);
                text_litros.setText("  " +TotalColeta);
                text_produtor.setText("  "+Nprodutor);
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
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void listaId() {
        text_capacidade=(TextView)findViewById(R.id.text_capacidade);
        text_litros=(TextView)findViewById(R.id.text_produtores);
        text_produtor=(TextView)findViewById(R.id.litros_caminhao);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Lista De Coletas");
        coletasList = new ArrayList<>();
        Intent intent =getIntent();
        idDaRota=intent.getStringExtra(RotaActivity.ROTA_ID);

        listViewColetas= (ListView) findViewById(R.id.list_coleta);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
    }



    @Override
    protected void onResume() {
        super.onResume();
        aux =(String) getIntent().getSerializableExtra("capacidade");
        Nprodutor=0;
        TotalColeta=0;

    }
}
