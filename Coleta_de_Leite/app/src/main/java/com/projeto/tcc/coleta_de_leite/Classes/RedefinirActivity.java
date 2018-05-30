package com.projeto.tcc.coleta_de_leite.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.projeto.tcc.coleta_de_leite.R;

import static android.app.ProgressDialog.show;

public class RedefinirActivity extends AppCompatActivity {
    AutoCompleteTextView email;
    FirebaseAuth auth;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    Button btn_reset;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refinir);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Enviando redefinição");
        progressDialog.setCancelable(false);
        linearLayout = (LinearLayout) findViewById(R.id.linear_redefinir);
        email = (AutoCompleteTextView) findViewById(R.id.redefinir_email);
        btn_reset = (Button) findViewById(R.id.btn_redefinir);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString().trim();

                if (TextUtils.isEmpty(strEmail)) {
                    email.setError("Obrigatorio");
                    return;
                }
                progressDialog.show();
                redefinir(strEmail);
            }
        });


    }

    private void redefinir(String strEmail) {


        auth.sendPasswordResetEmail(strEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            email.setText("");

                            finish();
                        } else {
                            if(isOnline()) {
                                snackbar( "Email não Cadastrado");
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
