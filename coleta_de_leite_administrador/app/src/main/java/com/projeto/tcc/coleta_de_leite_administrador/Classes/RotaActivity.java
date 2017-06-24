package com.projeto.tcc.coleta_de_leite_administrador.Classes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.tcc.coleta_de_leite_administrador.Adapter.RotaAdapter;
import com.projeto.tcc.coleta_de_leite_administrador.Dao.RotaDao;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Rota;
import com.projeto.tcc.coleta_de_leite_administrador.R;

import java.util.ArrayList;
import java.util.List;

public class RotaActivity extends AppCompatActivity {
    public static final String ROTA_ID="com.projeto.tcc.coleta_de_leite.rotaid";
    public static final String motoristaId="com.projeto.tcc.coleta_de_leite.motoristaId";
    ListView listViewRota;
    FrameLayout framevazio,frameLista;
    DatabaseReference databaseRotas;
    List<Rota> rotaList;
    int aux;
    String auxstr;

    String idmotorista;
    private FirebaseAuth auth;

    private ProgressDialog progressDialog;
    private FloatingActionButton fab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);
        Intent intent=getIntent();

        idmotorista=intent.getStringExtra(MotoristaActivity.motoristaId);
        databaseRotas= FirebaseDatabase.getInstance().getReference("rotas").child(idmotorista);
        auth = FirebaseAuth.getInstance();
        listarIds();




    }
    private void listarIds() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rota);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando rotas...");
        progressDialog.show();
        listViewRota=(ListView) findViewById(R.id.list_rota);
        rotaList= new ArrayList<>();
        frameLista=(FrameLayout)findViewById(R.id.frame_lista);
        framevazio=(FrameLayout)findViewById(R.id.frame_vazio);

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
            Intent intent=new Intent(RotaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(id==R.id.action_settings){
            buscaDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    private void buscaDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_busca, null);
        dialogBuilder.setView(dialogView);
        final EditText data=(EditText) dialogView.findViewById(R.id.edit_busca);
        final Button buttonAceitar = (Button) dialogView.findViewById(R.id.button_aceitar);
        final Button buttonCancelar = (Button) dialogView.findViewById(R.id.button_cancelar);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxstr=data.getText().toString().trim();

                buscaPorData(auxstr);

            }


        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void buscaPorData(final String data) {

        databaseRotas.addValueEventListener(new ValueEventListener() {


            public void onDataChange(DataSnapshot dataSnapshot) {


                rotaList.clear();
                for(DataSnapshot rotaSnapShot:dataSnapshot.getChildren()){

                    Rota rota =rotaSnapShot.getValue(Rota.class);
                    Toast.makeText(getApplicationContext(),""+data,Toast.LENGTH_LONG).show();
                  if (data.equals(rota.getHoraRota())){
                    rotaList.add(rota);
                  }

                }

                RotaAdapter rotaAdapter = new RotaAdapter(RotaActivity.this,rotaList);

                listViewRota.setAdapter(rotaAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

        databaseRotas.addValueEventListener(new ValueEventListener() {


            public void onDataChange(DataSnapshot dataSnapshot) {


                aux=0;
                rotaList.clear();
                for(DataSnapshot rotaSnapShot:dataSnapshot.getChildren()){

                    Rota rota =rotaSnapShot.getValue(Rota.class);
                    rotaList.add(rota);
                    aux = aux+ 1;
                }
                RotaAdapter rotaAdapter = new RotaAdapter(RotaActivity.this,rotaList);

                listViewRota.setAdapter(rotaAdapter);
                Toast.makeText(getApplicationContext(),"" +aux,Toast.LENGTH_LONG).show();
                if (aux==0){
                    frameLista.setVisibility(View.GONE);
                    framevazio.setVisibility(View.VISIBLE);

                }



                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        aux=0;


    }
}
