package com.projeto.tcc.coleta_de_leite.Classes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.R;

import static com.projeto.tcc.coleta_de_leite.Classes.ColetaActivity.rotaId;

/**
 * Created by raphael on 31/05/17.
 */

public class OcorrenciaActivity extends AppCompatActivity {
    ImageView imageView;
    String rotaid;
    TextView matricula,produtor,obs;
    TextClock horario;
    Button save;
    Spinner spinner;
    DatabaseReference databaseRotas;
    private static final int CAMERA_REQUESTE_CODE=1;
    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencia);
        findViewByIds();
        metodobotoes();
        Intent intent=getIntent();
        rotaid=(intent.getStringExtra(rotaid));
        progressDialog = new ProgressDialog(this);
        databaseRotas = FirebaseDatabase.getInstance().getReference("coletas").child(rotaid);
        mStorage= FirebaseStorage.getInstance().getReference();



    }
    private boolean verificaCampos() {
        if (matricula.getText().toString().isEmpty()) {
            matricula.setError(getString(R.string.vazio));
            return false;

        }
        if (produtor.getText().toString().isEmpty()) {

            produtor.setError(getString(R.string.vazio));
            return false;}
        if (obs.getText().toString().isEmpty()) {

            obs.setError(getString(R.string.vazio));
            return false;}



        return true;
    }


    private void metodobotoes() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUESTE_CODE);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampos()){
                    saveColeta();
                    finish();}

            }

            
        });
    }

    private void saveColeta() {
        String obs="";
        String nomeProdutor=produtor.getText().toString().trim();
        String mat = matricula.getText().toString().trim();
        String hora=horario.getText().toString().trim();
        String id =databaseRotas.push().getKey();
        String mSpinner=spinner.getSelectedItem().toString();
        Coletas coletas= new Coletas(id,rotaid,nomeProdutor,"0",mat,hora,"Nao Realizado","Nao Realizado","Não Realizado","Nao Realizado",obs,mSpinner,"");
        databaseRotas.child(id).setValue(coletas);
        finish();
    }

    protected  void findViewByIds() {
        imageView = (ImageView) findViewById(R.id.foto);
        matricula=(TextView) findViewById(R.id.ocorrencia_matricula);
        produtor=(TextView) findViewById(R.id.ocorrencia_produtor);
        obs=(TextView)findViewById(R.id.ocorrencia_obs);
        spinner=(Spinner)findViewById(R.id.spinnerOcorrencia);
        horario=(TextClock)findViewById(R.id.ocorrencia_hora);
        save=(Button)findViewById(R.id.button_ocorrencia);




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUESTE_CODE && resultCode == RESULT_OK) {
            progressDialog.setMessage("Carregando Imagem...");
//            progressDialog.show();
            Uri uri = data.getData();
            StorageReference arquivo = mStorage.child("photo").child(uri.getLastPathSegment());
            arquivo.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"show ",Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
}
