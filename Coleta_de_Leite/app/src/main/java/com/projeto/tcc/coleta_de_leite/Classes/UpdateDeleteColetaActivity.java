
        package com.projeto.tcc.coleta_de_leite.Classes;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;

        import com.google.android.gms.ads.AdView;
        import com.google.firebase.database.DatabaseReference;
        import com.projeto.tcc.coleta_de_leite.Dao.ColetaDao;
        import com.projeto.tcc.coleta_de_leite.Model.Coletas;
        import com.projeto.tcc.coleta_de_leite.R;
        import com.projeto.tcc.coleta_de_leite.Utils.AdMob;

        /**
 * Created by raphael on 19/05/17.
 */

public class UpdateDeleteColetaActivity extends AppCompatActivity{
    private EditText medida;
    private EditText produtor;
    private EditText litros;
    private EditText obs;
    private TextView amostra;
    private Button atualizaColeta;
    private Button deletaColeta;
    private String rotaid;
    private Spinner spinner;
    private Spinner spinnerAlizarol;
    DatabaseReference databaseRotas;
    String retifica;
    String idRota;
    String idColeta;
    final  ColetaDao coletaDao=new ColetaDao();;
     Coletas coletas;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizacoleta);
        coletas = (Coletas) getIntent().getSerializableExtra("dados");
        AdView mAdView = (AdView)findViewById(R.id.adView);
        final AdMob adMob = new AdMob();
        adMob.mAdmob(getApplicationContext(),mAdView);

        findViewByIds();

        medida.setText(coletas.getMedida());
        produtor.setText(coletas.getNomeProdutor());
        litros.setText(coletas.getLitrosColeta());
        amostra.setText(coletas.getAmostra());
        retifica="Registro Retificado";
        idRota= coletas.getIdRota();
        idColeta= coletas.getIdColeta();



        deletaColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              DeleteDialog();
            }
        });
        atualizaColeta.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                UpdateDialog();
            }
        });




    }
    private boolean verificaCampos() {
        if (medida.getText().toString().isEmpty()) {
            medida.setError(getString(R.string.vazio));
            return false;

        }
        if (produtor.getText().toString().isEmpty()) {

            produtor.setError(getString(R.string.vazio));
            return false;}
        if (amostra.getText().toString().isEmpty()) {

            amostra.setError(getString(R.string.vazio));
            return false;}
        if (obs.getText().toString().isEmpty()) {

            obs.setError(getString(R.string.vazio));
            return false;}


        if (litros.getText().toString().isEmpty()) {

            litros.setError(getString(R.string.vazio));
            return false;}



        return true;
    }




    private void findViewByIds() {
        obs=(EditText)findViewById(R.id.edit_update_obs);
        deletaColeta=(Button)findViewById(R.id.button_deletar_coleta);
        atualizaColeta=(Button)findViewById(R.id.button_atualiza_coleta);
        amostra=(EditText)findViewById(R.id.edit_update_amostra);
        medida =(EditText)findViewById(R.id.edit_update_medida);
        produtor=(EditText)findViewById(R.id.edit_update_prod);
        litros=(EditText)findViewById(R.id.edit_update_litros);
        spinnerAlizarol=(Spinner)findViewById(R.id.spinner_update_Alizarol);
        spinner=(Spinner)findViewById(R.id.spinner_update_Temperatura);
    }

    private void DeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_delete, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAceitar = (Button) dialogView.findViewById(R.id.button_aceitar);
        final Button buttonCancelar = (Button) dialogView.findViewById(R.id.button_cancelar);

        dialogBuilder.setTitle("ATENCAO");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coletaDao.deleteColeta(idRota,idColeta);
                finish();
            }
        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
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

        if(verificaCampos()) {
            final   String sobs = obs.getText().toString().trim();
            final   String id = coletas.getIdColeta();
            final  String rotaId = coletas.getIdRota();
            final   String nomeProdutor = produtor.getText().toString().trim();
            final  String litragem = litros.getText().toString().trim();
            final  String mat = medida.getText().toString().trim();
            final  String alizarol = spinnerAlizarol.getSelectedItem().toString();
            final   String temperatura = spinner.getSelectedItem().toString();
            final   String namostra = amostra.getText().toString().trim();
            final   String retificado = "Registro Retificado";
            final    String Horario= coletas.getHoraColeta();
            b.show();
        buttonAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coletaDao.updateColeta(id, rotaId, nomeProdutor, litragem, mat, Horario, alizarol, temperatura, namostra, retificado, sobs,"","");
                    finish();
                } }



        );
        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }}
}






