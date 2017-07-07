package com.projeto.tcc.coleta_de_leite.Classes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth auth;

    private ProgressDialog progressDialog;
    private FloatingActionButton fab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);
        Intent intent=getIntent();

        idmotorista=intent.getStringExtra(LoginActivity.motoristaId);

        databaseRotas= FirebaseDatabase.getInstance().getReference("rotas").child(idmotorista);
        auth = FirebaseAuth.getInstance();
        listarIds();
        metodosBotoes();



    }
    private void listarIds() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rota);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando rotas...");
        progressDialog.show();
        listViewRota=(ListView) findViewById(R.id.list_rota);
        listViewRota.setDivider(this.getResources().getDrawable(R.drawable.transperent_color));
        listViewRota.setDividerHeight(20);
        rotaList= new ArrayList<>();
        fab = (FloatingActionButton) findViewById(R.id.fab);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rotas,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_ajuda) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"suporteeliteleitera@hotmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "SOLICITAÇAO DE AJUDA ");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

        }
        if (id == R.id.actio_sair) {
            auth.signOut();
            Intent intent=new Intent(RotaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseRotas.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {



                rotaList.clear();
                for(DataSnapshot rotaSnapShot:dataSnapshot.getChildren()){

                    Rota rota =rotaSnapShot.getValue(Rota.class);
                    rotaList.add(rota);
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
    protected void onResume() {
        super.onResume();


    }
}
