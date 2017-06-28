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
import android.widget.Toast;

import com.projeto.tcc.coleta_de_leite_administrador.Classes.ColetaActivity;
import com.projeto.tcc.coleta_de_leite_administrador.Classes.RotaActivity;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Motorista;
import com.projeto.tcc.coleta_de_leite_administrador.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael on 21/06/17.
 */

public class MotoristaAdapter extends ArrayAdapter<Motorista> {
    private Activity context;
    List<Motorista> motorista1;
    public static final String motoristaId="com.projeto.tcc.coleta_de_leite.motoristaId";


    public MotoristaAdapter(Activity context, List<Motorista> motorista ) {
        super(context, R.layout.layout_motorista,motorista);
        this.context=context;
        this.motorista1=motorista;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem= inflater.inflate(R.layout.layout_motorista,null,true);
        TextView text_nome= (TextView) listViewItem.findViewById(R.id.text_nome);
        TextView text_email= (TextView)listViewItem.findViewById(R.id.text_email);
        TextView textViewColeta= (TextView)listViewItem.findViewById(R.id.textViewColetar);
        TextView text_matricula=(TextView)listViewItem.findViewById(R.id.text_matricula);
        final Motorista motorista = motorista1.get(position);
        text_nome.setText(motorista.getNome());
        text_email.setText(motorista.getEmailmotorista());
        text_matricula.setText(motorista.getIdmotorista());
        final String aux=(motorista.getIdentificador());

        textViewColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RotaActivity.class);
            intent.putExtra(motoristaId,aux);
                Toast.makeText(getContext(),aux,Toast.LENGTH_LONG).show();
                context.startActivity(intent);

            }});




        return listViewItem;
    }



}
