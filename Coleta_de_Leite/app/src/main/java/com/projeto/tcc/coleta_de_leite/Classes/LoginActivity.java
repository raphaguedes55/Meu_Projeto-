package com.projeto.tcc.coleta_de_leite.Classes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private EditText senha, email;
    private LinearLayout linearLayout;
    private TextView redefinir, criarconta;
    private Button login;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    DatabaseReference databaseMotorista;
    public static final String motoristaId = "com.projeto.tcc.coleta_de_leite.motoristaId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando Usuario");
        progressDialog.setCancelable(false);


        databaseMotorista = FirebaseDatabase.getInstance().getReference("motoristas");
        verificaAuth();


        buscadeid();
        metodobotoes();
    }


    public void metodobotoes() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String inputemail = email.getText().toString();
                final String password = senha.getText().toString();
                if (TextUtils.isEmpty(inputemail)) {
                    email.setError("Obrigatorio");
                    snackbar("Insira seu Email ");

                    progressDialog.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    senha.setError("Obrigatorio");
                    snackbar("Insira sua senha ");

                    progressDialog.dismiss();
                    return;
                }


                auth.signInWithEmailAndPassword(inputemail, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    snackbar("Usuario ou senha invalidos ");
                                    vibrar();

                                    progressDialog.dismiss();


                                } else {
                                    verificaAuth();
                                }


                            }
                        });
            }
        });
        redefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transicaoadc = new Intent(LoginActivity.this, RedefinirActivity.class);
                startActivity(transicaoadc);

            }
        });
        criarconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transicaoadc = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(transicaoadc);


            }
        });
    }


    public void buscadeid() {
        linearLayout = (LinearLayout) findViewById(R.id.linear_login);
        email = (EditText) findViewById(R.id.login_Email);
        senha = (EditText) findViewById(R.id.loginSenha);
        redefinir = (TextView) findViewById(R.id.text_redefinir);
        criarconta = (TextView) findViewById(R.id.text_criar_conta);
        login = (Button) findViewById(R.id.logar);
    }


    public void verificaAuth() {
        if (auth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String token = user.getUid();
                Motoristas motoristas = new Motoristas(token, "","", "");
                Intent transicaoadc = new Intent(LoginActivity.this, RotaActivity.class);
                transicaoadc.putExtra(motoristaId, motoristas.getId_motorista());
                startActivity(transicaoadc);
                progressDialog.dismiss();
                finish();

            }


        }
    }

    private void snackbar(final String text) {
        Snackbar snackbar = Snackbar.make(linearLayout, text, Snackbar.LENGTH_SHORT);
        progressDialog.dismiss();
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.RED);
        snackbar.show();
        vibrar();


    }

    public void vibrar() {
        Vibrator rr = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        long milliseconds = 100;//'300' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        rr.vibrate(milliseconds);
    }

}

