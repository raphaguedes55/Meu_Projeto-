package com.projeto.tcc.coleta_de_leite_administrador.Classes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    EditText edit_matricula,edit_nome, edit_email,edit_senha,edit_verifica;
    Button cadastrar;
    private FirebaseAuth auth;
    DatabaseReference databaseMotorista;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        campos();
        auth=FirebaseAuth.getInstance();
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome=edit_nome.getText().toString().trim();
               final  String email = edit_email.getText().toString().trim();
                final String password = edit_senha.getText().toString().trim();
                String passwordV=edit_verifica.getText().toString().trim();
                final String matricula=edit_matricula.getText().toString().trim();
                if (password.equals(passwordV)){

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                    auth.createUserWithEmailAndPassword(email, password)

                            .addOnCompleteListener(NovoMotoristaActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                                    String token= user.getUid();
                                    Motorista   motorista=new Motorista(matricula ,email,nome,password,token);
                                    databaseMotorista.child(token).setValue(motorista);


                                    if (!task.isSuccessful()) {
                                        Toast.makeText(NovoMotoristaActivity.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        finish();
                                    }
                                }
                            });



            }
            else{
                    Toast.makeText(getApplicationContext(), "Senhas nao Iguais", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void campos() {
        databaseMotorista= FirebaseDatabase.getInstance().getReference("motoristas");
        edit_matricula=(EditText)findViewById(R.id.matricula);
        edit_nome=(EditText)findViewById(R.id.motorista);
        edit_email =(EditText)findViewById(R.id.email);
        edit_senha=(EditText)findViewById(R.id.senha);
        edit_verifica=(EditText)findViewById(R.id.verificasenha);
        cadastrar=(Button)findViewById(R.id.button_cadastrar);
    }
}
