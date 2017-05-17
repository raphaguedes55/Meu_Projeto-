package com.projeto.tcc.coleta_de_leite.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.tcc.coleta_de_leite.Classes.ColetaActivity;
import com.projeto.tcc.coleta_de_leite.Dao.RotaDao;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by raphael on 29/03/17.
 */

public class RotaAdapter extends ArrayAdapter<Rota> {
    private Activity context;
    private List<Rota> rotaList;

    public static final String ROTA_ID="com.projeto.tcc.coleta_de_leite.rotaid";

    public RotaAdapter(Activity context,List<Rota>rotaList){
        super(context, R.layout.layout_rota,rotaList);
        this.context=context;
        this.rotaList=rotaList;

    }

    @NonNull
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.layout_rota,null,true);
        TextView textViewRota=(TextView) listViewItem.findViewById(R.id.textRota);
        TextView tipoDeRota=(TextView) listViewItem.findViewById(R.id.data_rota);
        TextView textViewColeta = (TextView) listViewItem.findViewById(R.id.textViewColetar);
        TextView textViewUpdadeColeta=(TextView)listViewItem.findViewById(R.id.textViewUpdate);
        final Rota rota=rotaList.get(position);
        textViewRota.setText(rota.getNomeRota());
        tipoDeRota.setText(rota.getTipoRota());

        textViewColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rota.getRotaId();
                Intent intent = new Intent(context,ColetaActivity.class);
                intent.putExtra(ROTA_ID,rota.getRotaId());
                context.startActivity(intent);

            }
        });
        textViewUpdadeColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDeleteDialog(rota.getRotaId(),rota.getNomeRota(),rota.getCapacidade(),rota.getHoraRota());
            }
        });

        return listViewItem;
    };
    private void showUpdateDeleteDialog(final String idRota, String nomeRota, String capacidadeRota, final String hora) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_dialogrota, null);
        dialogBuilder.setView(dialogView);

        final EditText editrotas = (EditText) dialogView.findViewById(R.id.editUpdateNomeRota);
        final TextView editcarga = (TextView) dialogView.findViewById(R.id.editUpdateCarga);
        final Spinner spinnerTipoRota=(Spinner)dialogView.findViewById(R.id.spinnerUpdateRota);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(nomeRota);
        editrotas.setText(nomeRota);
        editcarga.setText(capacidadeRota);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editrotas.getText().toString().trim();
                String carga= editcarga.getText().toString().trim();
                String tipo = spinnerTipoRota.getSelectedItem().toString();


                    updateArtist(idRota, name,carga,tipo,hora);
                b.dismiss();

            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotaDao rotaDao=new RotaDao();
                deleteRota(idRota);
                b.dismiss();
                atualiza(rotaList);

            }
        });
    }
    private boolean deleteRota(String id) {
        //getting the specified artist reference
        DatabaseReference dR =  FirebaseDatabase.getInstance().getReference("rotas").child(id);

        //removing artist
        dR.removeValue();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("coletas").child(id);


        databaseReference.removeValue();
        Toast.makeText(context.getApplicationContext(), "Artist Deleted "+id, Toast.LENGTH_LONG).show();

        return true;
    }
    private boolean updateArtist(String id, String name, String genre,String TipoRota, String Data) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("rotas").child(id);
        Rota rota = new Rota(id,name, genre,TipoRota,Data);
        dR.setValue(rota);
        Toast.makeText(context.getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;

        }


    public void atualiza(List<Rota> rotaList) {
        this.rotaList=rotaList;
        notifyDataSetChanged();}
}


