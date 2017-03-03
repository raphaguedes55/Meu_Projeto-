package com.powellapps.ihadream;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by matheus on 28/02/17.
 */

public class InicioActivity extends AppCompatActivity {



    private Button btnEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_inicio);

        //UI
        btnEmail = (Button) findViewById(R.id.button_email);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity( new Intent(getApplicationContext(), RegisterEmailActivity.class));
            }
        });




    }
}
