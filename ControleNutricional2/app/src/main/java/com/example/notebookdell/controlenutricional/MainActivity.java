package com.example.notebookdell.controlenutricional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;

import com.example.notebookdell.controlenutricional.Utils.FragmentUtils;
import com.example.notebookdell.controlenutricional.fragments.InicioFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbarTopo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Controle Nutricional");



        FragmentUtils.replace(MainActivity.this, new InicioFragment());



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_logo, menu);
        return true;
    }

}