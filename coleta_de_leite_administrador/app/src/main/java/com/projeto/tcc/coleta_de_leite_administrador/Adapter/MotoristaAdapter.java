package com.projeto.tcc.coleta_de_leite_administrador.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projeto.tcc.coleta_de_leite_administrador.Model.Motorista;
import com.projeto.tcc.coleta_de_leite_administrador.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael on 21/06/17.
 */

public class MotoristaAdapter extends ArrayAdapter<Motorista> {
    private Activity context;
    List<Motorista> motorista1;

    public MotoristaAdapter(Activity context, List<Motorista> motorista ) {
        super(context, R.layout.layout_rota,motorista);
        this.context=context;
        this.motorista1=motorista;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem= inflater.inflate(R.layout.layout_rota,null,true);
        TextView text_nome= (TextView) listViewItem.findViewById(R.id.text_nome);
        TextView text_email= (TextView)listViewItem.findViewById(R.id.text_email);

        Motorista motorista = motorista1.get(position);
        text_nome.setText(motorista.getNome());
        text_email.setText(motorista.getEmailmotorista());


        return listViewItem;
    }



}
