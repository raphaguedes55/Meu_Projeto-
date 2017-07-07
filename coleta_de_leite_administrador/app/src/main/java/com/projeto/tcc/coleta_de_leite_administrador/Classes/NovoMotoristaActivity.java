package com.projeto.tcc.coleta_de_leite_administrador.Classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Motorista;
import com.projeto.tcc.coleta_de_leite_administrador.R;

/**
 * Created by raphael on 19/06/17.
 */

public class NovoMotoristaActivity extends AppCompatActivity {
    EditText edit_matricula, edit_nome, edit_email, edit_senha, edit_verifica;
    Button cadastrar;
    LinearLayout linearLayoutr;
    private FirebaseAuth auth;
    DatabaseReference databaseMotorista;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        listadeId();
        databaseMotorista = FirebaseDatabase.getInstance().getReference("motoristas");
        auth = FirebaseAuth.getInstance();
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incluirMotorista();
            }

        });
    }


    private void listadeId() {
        linearLayoutr = (LinearLayout) findViewById(R.id.linear_cadastro);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cadastrando Usuario...");
        progressDialog.setCancelable(false);
        edit_matricula = (EditText) findViewById(R.id.matricula);
        edit_nome = (EditText) findViewById(R.id.motorista);
        edit_email = (EditText) findViewById(R.id.email);
        edit_senha = (EditText) findViewById(R.id.senha);
        edit_verifica = (EditText) findViewById(R.id.verificasenha);
        cadastrar = (Button) findViewById(R.id.button_editar);
    }

    private void incluirMotorista() {
        progressDialog.show();

        final String nome = edit_nome.getText().toString().trim();
        final String email = edit_email.getText().toString().trim();
        final String password = edit_senha.getText().toString().trim();
        final String passwordV = edit_verifica.getText().toString().trim();
        final String matricula = edit_matricula.getText().toString().trim();



            if (TextUtils.isEmpty(nome)) {
                snackbar("Nome vazio!");
                return;
            }
            if (TextUtils.isEmpty(matricula)) {
                snackbar("Matricula vazia!");
                return;
            }

            if (TextUtils.isEmpty(matricula)) {
                snackbar("Matricula deve conter 6 digistos!");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                snackbar("Email vazio!");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                snackbar("Insira sua senha!");
                return;
            }
            if (password.length() < 6) {
                snackbar("A senha deve ter no minimo 6 digitos!");
                return;
            }
            if (password.equals( passwordV)){}else {
                snackbar("As senhas sao diferentes");
                return;

            }


            auth.createUserWithEmailAndPassword(email, password)

                    .addOnCompleteListener(NovoMotoristaActivity.this, new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();


                            if (!task.isSuccessful()) {
                                snackbar("EMAIL JA CADASTRADO OU  INVALIDO");
                            } else {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(NovoMotoristaActivity.this);
                                alerta.setTitle("Confirmaçao de cadastro !");
                                alerta
                                        .setIcon(R.drawable.login)
                                        .setMessage(R.string.aviso_confirmaçao)
                                        .setCancelable(false)
                                        .setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                String token = user.getUid();
                                                Log.v("SENHA",password);
                                                Log.v("VERIFICA",passwordV);
                                                Motorista motorista = new Motorista(matricula, token, email, nome, password);
                                                databaseMotorista.child(token).setValue(motorista);
                                                progressDialog.dismiss();
                                                vibrar();
                                                finish();


                                            }
                                        })
                                        .setNegativeButton("Cancelar", null)
                                ;
                                AlertDialog alertDialog = alerta.create();
                                alertDialog.show();


                            }


                            }
                        }
                    );




    }

    private void snackbar(final String text) {
        Snackbar snackbar = Snackbar.make(linearLayoutr, text, Snackbar.LENGTH_SHORT);
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

