package com.powellapps.calculadora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editvisor;
    Button somar ;
    Button igual;
    String primeiroNumero;
    String segundonumero;
    String operador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editvisor = (EditText) findViewById(R.id.edit_visor);
        somar =(Button) findViewById(R.id.button_somar);
        igual=(Button) findViewById(R.id.button_igual);

        somar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                primeiroNumero =editvisor.getEditableText().toString();
                Toast.makeText(getApplicationContext(),"numero 22 inseriro"+primeiroNumero,Toast.LENGTH_LONG);
                editvisor.setText("");

            }
        });
        igual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operador = "+";
                String segundonumero;
                segundonumero = editvisor.getEditableText().toString();
                int resultado = calcular();
                editvisor.setText(resultado);


            }
        });

    }
    private int calcular() {
        int primeiro = Integer.valueOf(primeiroNumero);
        int segundo = Integer.valueOf(segundonumero);
        if (operador == "+") {
            return primeiro + segundo;
        }
        else  if (operador == "-") {
        }
        return 0;}


}