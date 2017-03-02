package com.powellapps.ihadream;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.R.string.cancel;

public class RegisterEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonRegistrar;
    private EditText mSenha,mEmail,mNome;
    private ProgressDialog mProgressDialog;
    private TextView textViewAcessar;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        //objeto firebase autenticando
        firebaseAuth = FirebaseAuth.getInstance();

        //caso usuario não esteja logado ainda
        if (firebaseAuth.getCurrentUser() != null ){
            finish();
            startActivity(new Intent(getApplicationContext(), DesejosActivity.class));
        }


        //UI
        mEmail = (EditText)findViewById(R.id.email);
        mNome = (EditText)findViewById(R.id.name);
        mSenha = (EditText)findViewById(R.id.password);
        buttonRegistrar =(Button)findViewById(R.id.button_registrar);
        textViewAcessar = (TextView)findViewById(R.id.textViewSignin);
        buttonRegistrar.setOnClickListener(this);
        textViewAcessar.setOnClickListener(this);
        mProgressDialog = new ProgressDialog(this);


        //resetErros
        mSenha.setError(null);
        mNome.setError(null);
        mEmail.setError(null);

    }
    private boolean validacaoSenha(String s) {
        //TODO: Replace this with your own logic
        return s.length() > 4;
    }
    private boolean validacaoEmail(String e) {
        //TODO: Replace this with your own logic
        return e.contains("@");


    }
    private boolean validacaoNome(String n) {
        return n.length()> 2;
    }



    private  void RegistraUsuario(){
        String nome = mNome.getText().toString();
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
        //checando e validando nome
        if (!TextUtils.isEmpty(nome) && !validacaoNome(nome)){

            mNome.setError(getString(R.string.error_field_required));
            focusView = mNome;
            cancel= true;
        }else  if (!validacaoNome(nome)){
            mNome.setError( getString(R.string.error_field_required));
            focusView = mNome;
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

            Toast.makeText(getApplicationContext(),"OK ok ",Toast.LENGTH_LONG).show();
            //criando um novo usuario

            firebaseAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), DesejosActivity.class));
                            }else {
                                Toast.makeText(RegisterEmailActivity.this, getString(R.string.error_registro), Toast.LENGTH_LONG).show();

                            }mProgressDialog.dismiss();
                        }
                    });








        }


    }



    @Override
    public void onClick(View v) {
        if (v == buttonRegistrar){
            RegistraUsuario();
        }
        if (v== textViewAcessar){
            startActivity(new Intent(getApplicationContext(), LoginEmailActivity.class));
        }

    }
}
