package com.projeto.tcc.coleta_de_leite_administrador.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projeto.tcc.coleta_de_leite_administrador.Classes.ColetaActivity;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Rota;
import com.projeto.tcc.coleta_de_leite_administrador.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by raphael on 29/03/17.
 */

public class RotaAdapter extends ArrayAdapter<Rota> {
    private Activity context;
    private List<Rota> rotaList;

    public static final String ROTA_ID="com.projeto.tcc.coleta_de_leite.rotaid";
   public String aux;

    public RotaAdapter(Activity context,List<Rota>rotaList){
        super(context, R.layout.layout_rota,rotaList);
        this.context=context;
        this.rotaList=rotaList;
        Collections.reverse(rotaList);


    }



    @NonNull
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.layout_rota,null,true);
        TextView textViewRota=(TextView) listViewItem.findViewById(R.id.textRota);
        TextView textViewHora=(TextView) listViewItem.findViewById(R.id.data_rota);
        TextView textViewColeta = (TextView) listViewItem.findViewById(R.id.textViewColetar);
        final Rota rota=rotaList.get(position);
        textViewRota.setText(rota.getNomeRota());
         textViewHora.setText(rota.getHoraRota());
        textViewColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rota.getRotaId();
                Intent intent = new Intent(context,ColetaActivity.class);
                intent.putExtra(ROTA_ID,rota.getRotaId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("capacidade",rota.getCapacidade());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });



        return listViewItem;
    };


}


