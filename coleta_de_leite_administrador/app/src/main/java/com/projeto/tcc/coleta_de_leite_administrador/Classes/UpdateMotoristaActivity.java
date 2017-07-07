package com.projeto.tcc.coleta_de_leite_administrador.Classes;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite_administrador.Dao.MotoristaDao;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Motorista;
import com.projeto.tcc.coleta_de_leite_administrador.R;

/**
 * Created by raphael on 03/07/17.
 */

public class UpdateMotoristaActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    EditText nome_motorista;
    EditText matricula;
    EditText email;
    Button button_editar,button_salvar,button_cancelar;
    Motorista motorista;
    DatabaseReference databaseMotorista;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_motorista);
        motorista  =(Motorista) getIntent().getSerializableExtra("edit_motorista");

        listadeId();
        setText();
        metodosbotoes();



    }

    private void metodosbotoes() {
        button_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_editar.setVisibility(View.GONE);
                button_salvar.setVisibility(View.VISIBLE);
                button_cancelar.setVisibility(View.VISIBLE);
                nome_motorista.setEnabled(true);
                matricula.setEnabled(true);
            }
        });
        button_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDialog();
          }

        });
        button_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();


            }
        });
    }

    protected void setText() {
        email.setText(motorista.getEmailmotorista());
        nome_motorista.setText(motorista.getNome());
        matricula.setText(motorista.getmotoristaId());
        email.setEnabled(false);
        nome_motorista.setEnabled(false);
        matricula.setEnabled(false);
    }

    private void listadeId() {
        email=(EditText)findViewById(R.id.edi_email_motorista);
        nome_motorista = (EditText)findViewById(R.id.edit_nome_motorista);
        matricula=(EditText)findViewById(R.id.edit_matricula_motorista);
        linearLayout=(LinearLayout)findViewById(R.id.linear_edit);
        button_salvar=(Button)findViewById(R.id.button_salvar_motorista);
        button_editar=(Button)findViewById(R.id.button_editar_motorista);
        button_cancelar=(Button)findViewById(R.id.button_cancelar_motorista);

    }

    private boolean verificaCampos() {
        if (TextUtils.isEmpty(""+nome_motorista)) {
            snackbar("Nome vazio!");
            return false;
        }
        if (TextUtils.isEmpty(""+matricula)) {
            snackbar("Matricula vazia!");
            return false;
        }

        if (TextUtils.isEmpty(""+matricula)) {
            snackbar("Matricula deve conter 6 digistos!");
            return false;
        }

        return true;
    }
    private void UpdateDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAceitar = (Button) dialogView.findViewById(R.id.button_aceitar);
        final Button buttonDeletar = (Button) dialogView.findViewById(R.id.button_cancelar);

        dialogBuilder.setTitle("ATENCAO");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampos()) {
                    String motoristaid = motorista.getMatricula();
                    String mat = "" + matricula.getText().toString().trim();
                    String email = "" + motorista.getEmailmotorista();
                    String nome = "" + nome_motorista.getText().toString().trim();
                    String senha = "" + motorista.getSenha();
                    Log.v("matricula ", mat);
                    Log.v("idMotorista", motoristaid);
                    final MotoristaDao motoristaDao = new MotoristaDao();
                    motoristaDao.UpdateMotorista(motoristaid, mat, email, nome, senha);
                    finish();

                    ;
                }

            }
        });
        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }
    private void snackbar(final String text) {
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
