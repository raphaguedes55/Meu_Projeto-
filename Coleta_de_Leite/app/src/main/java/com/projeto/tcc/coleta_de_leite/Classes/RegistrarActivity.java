package com.projeto.tcc.coleta_de_leite.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Model.Motoristas;
import com.projeto.tcc.coleta_de_leite.R;

public class RegistrarActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText editLogin;
    EditText editPassword;
    EditText editName;
    Button btnLoginRegister;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    DatabaseReference databaseMotorista;
    public static final String motoristaId = "com.projeto.tcc.coleta_de_leite.motoristaId";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novologin);
        auth = FirebaseAuth.getInstance();
        databaseMotorista = FirebaseDatabase.getInstance().getReference("motoristas");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando Usuario");
        progressDialog.setCancelable(false);
        buscadeId();
    }

    private void buscadeId() {
        editName= (EditText)findViewById(R.id.novo_nome);
        editLogin = (EditText) findViewById(R.id.novo_email);
        editPassword = (EditText) findViewById(R.id.nova_senha);
        btnLoginRegister = (Button) findViewById(R.id.registrar);
        linearLayout = (LinearLayout) findViewById(R.id.linear_registrar);

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = editLogin.getText().toString().trim();
                final String nome = editName.getText().toString().trim();
                final String password = editPassword.getText().toString().trim();
                if (TextUtils.isEmpty(nome)) {
                    editName.setError("Obrigatorio");
                    snackbar("Insira seu Nome");

                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    editLogin.setError("Obrigatorio");
                    snackbar("Insira seu Email");

                    return;
                }

                if (password.length() < 6) {
                    editPassword.setError("Obrigatorio");
                    snackbar("A senha deve ter no minimo 6 digitos!");
                    return;
                }

                registerUser(email,nome,password);

            }
        });
    }

    private void registerUser(final String email,final String name,final String password) {
        progressDialog.show();


        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String token = user.getUid();
                            Motoristas motoristas = new Motoristas(token,name,email, password);
                            databaseMotorista.child(token).setValue(motoristas);
                            Intent transicaoadc = new Intent(RegistrarActivity.this, LoginActivity.class);
                            transicaoadc.putExtra(motoristaId, motoristas.getId_motorista());
                            startActivity(transicaoadc);

                            finish();
                        } else {
                            if(isOnline()) {
                                editLogin.setText("");
                                editName.setText("");
                                editPassword.setText("");
                                snackbar( "Email ja Cadastrado");
                                vibrar();
                                return;
                            }

                            snackbar("Sem conexao de internet");
                            vibrar();
                        }
                    }
                });
    }
    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();

    }

    private void snackbar(final String text) {
        progressDialog.dismiss();
        Snackbar snackbar = Snackbar.make(linearLayout, text, Snackbar.LENGTH_SHORT);
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
