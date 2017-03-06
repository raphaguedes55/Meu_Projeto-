package com.powellapps.ihadream;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private  Button buttonAcessar;
    private  EditText mEmail, mSenha;
    private TextView textViewRegistrar;


    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);


        //auth Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //se o metodo getCurrentUser não for nunlo
        //significa que o usuario ja esta logado

        if (firebaseAuth.getCurrentUser() != null) {
            //fecha esta activity
            finish();

            //inicia a lista de desejos
            startActivity(new Intent(getApplicationContext(), DesejosActivity.class));

        }

        //UI
        mEmail = (EditText) findViewById(R.id.email_login);
        mSenha = (EditText) findViewById(R.id.password_email);
        textViewRegistrar = (TextView) findViewById(R.id.textViewRegistrar);
        progressDialog = new ProgressDialog(this);
        buttonAcessar = (Button)findViewById(R.id.button_acessar);

        //resetErros
        mSenha.setError(null);
        mEmail.setError(null);

        //anexando clicks
        buttonAcessar.setOnClickListener(this);
        textViewRegistrar.setOnClickListener(this);

    }

    private boolean validacaoSenha(String s) {
        //TODO: Replace this with your own logic
        return s.length() > 4;
    }
    private boolean validacaoEmail(String e) {
        //TODO: Replace this with your own logic
        return e.contains("@");


    }

    //metodo de login
    private void userLogin() {

        String email = mEmail.getText().toString();
        String senha = mSenha.getText().toString();

        boolean cancel = false;
        View focusView = null;


        //checando e validando senha
        if (!TextUtils.isEmpty(senha) && !validacaoSenha(senha)) {
            mSenha.setError(getString(R.string.error_invalid_password));
            focusView = mSenha;
            cancel = true;
        }

        // checando e validando email
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!validacaoEmail(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        if (cancel) {
            //caso algum campo não esteja devidamente correto
            focusView.requestFocus();
        } else {

            progressDialog.setMessage(getString(R.string.msg_progress));
            progressDialog.show();

            //logando
            firebaseAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            //se tiver sucesso
                            if (task.isSuccessful()){
                                //starta a lista de desejos
                                finish();
                                startActivity(new Intent(getApplicationContext(), DesejosActivity.class));
                            }else {
                                Toast.makeText(getApplicationContext(), "Dados invalidos", Toast.LENGTH_LONG).show();
                            }


                        }
                    });




        }


    }


    @Override
    public void onClick(View v) {
        if (v == buttonAcessar){
            userLogin();
        }
        if (v== textViewRegistrar)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), RegisterEmailActivity.class));
        }
    }
}

