package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Model.Motoristas;
import com.projeto.tcc.coleta_de_leite.R;

/**
 * Created by raphael on 28/03/17.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText senha,email;
    private Button login;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    DatabaseReference databaseMotorista;
    public static final String motoristaId="com.projeto.tcc.coleta_de_leite.motoristaId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseMotorista= FirebaseDatabase.getInstance().getReference("motoristas");

        verificaAuth();
        buscadeid();
        metodobotoes();
    }






    public void metodobotoes(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String inputemail = email.getText().toString();
                final String password= senha.getText().toString();
                if (TextUtils.isEmpty(inputemail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }





                auth.signInWithEmailAndPassword(inputemail,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()){
                                    senha.setError(getString(R.string.senha_invalida));


                                }

                                else{
                                    progressBar.setVisibility(View.VISIBLE);
                                    login.setVisibility(View.GONE);
                                    Intent intent=new Intent(LoginActivity.this, RotaActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }





    public void buscadeid(){
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        email=(EditText)findViewById(R.id.login_Email);
        senha=(EditText)findViewById(R.id.loginSenha);
        login=(Button)findViewById(R.id.logar);}


    public void verificaAuth(){
        if(auth.getCurrentUser() !=null){
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            if (user!=null){
                String token= user.getUid();
                Motoristas motoristas= new Motoristas(token);
               databaseMotorista.child(token).setValue(motoristas);
                Intent transicaoadc = new Intent(LoginActivity.this,RotaActivity.class);
                transicaoadc.putExtra(motoristaId,token);
                startActivity(transicaoadc);
                finish();

            }


        }

    }

}

