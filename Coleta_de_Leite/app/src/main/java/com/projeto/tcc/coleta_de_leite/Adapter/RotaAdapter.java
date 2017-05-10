package com.projeto.tcc.coleta_de_leite.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

import java.util.List;

/**
 * Created by raphael on 29/03/17.
 */

public class RotaAdapter extends ArrayAdapter<Rota> {
    private Activity context;
    private List<Rota> rotaList;

    public RotaAdapter(Activity context,List<Rota>rotaList){
        super(context, R.layout.rota_layout,rotaList);
        this.context=context;
        this.rotaList=rotaList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.rota_layout,null,true);
        TextView textViewRota=(TextView) listViewItem.findViewById(R.id.textRota);
        TextView tipoDeRota=(TextView) listViewItem.findViewById(R.id.data_rota);
        Rota rota=rotaList.get(position);
        textViewRota.setText(rota.getNomeRota());
        tipoDeRota.setText(rota.getTipoRota());

        return listViewItem;
    };


}
