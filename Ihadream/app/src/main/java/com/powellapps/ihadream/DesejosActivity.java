package com.powellapps.ihadream;

<<<<<<< HEAD
import android.app.Activity;
=======
>>>>>>> 12f129f6e90c2007beba58da386ab7d967951752
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

<<<<<<< HEAD
import com.facebook.login.LoginManager;
=======
>>>>>>> 12f129f6e90c2007beba58da386ab7d967951752
import com.google.firebase.auth.FirebaseAuth;

public class DesejosActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;



    //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desejos, menu);
        return super.onCreateOptionsMenu(menu);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "metodo a ser impementado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actio_sair:
<<<<<<< HEAD
              logout();
=======
                finish();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), InicioActivity.class));
>>>>>>> 12f129f6e90c2007beba58da386ab7d967951752


                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desejos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();


    }
<<<<<<< HEAD
    private void goLoginScreen() {
        Intent intent = new Intent(this,InicioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void logout (){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();

}
}
=======

}
>>>>>>> 12f129f6e90c2007beba58da386ab7d967951752
