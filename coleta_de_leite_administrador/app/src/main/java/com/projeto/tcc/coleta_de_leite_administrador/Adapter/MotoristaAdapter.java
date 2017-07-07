package com.projeto.tcc.coleta_de_leite_administrador.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import com.projeto.tcc.coleta_de_leite_administrador.Classes.RotaActivity;
import com.projeto.tcc.coleta_de_leite_administrador.Classes.UpdateMotoristaActivity;
import com.projeto.tcc.coleta_de_leite_administrador.Model.Motorista;
import com.projeto.tcc.coleta_de_leite_administrador.R;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.layout_motorista,null,true);
        TextView text_nome= (TextView) listViewItem.findViewById(R.id.text_nome);
        TextView text_email= (TextView)listViewItem.findViewById(R.id.text_email);
        TextView textViewColeta= (TextView)listViewItem.findViewById(R.id.textViewColetar);
        TextView textViewMotorista=(TextView)listViewItem.findViewById(R.id.textViewEditar);
        //TextView text_matricula=(TextView)listViewItem.findViewById(R.id.text_matricula);

        final Motorista motorista = motorista1.get(position);
        text_nome.setText(motorista.getNome());
        text_email.setText(motorista.getEmailmotorista());
       // text_matricula.setText(motorista.getmotoristaId());

        final String aux=(motorista.getMatricula());

        textViewColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              final  Motorista motorista2 = motorista1.get(position);
                Intent intent = new Intent(context,RotaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dados_motorista",motorista2);
                intent.putExtras(bundle);
               context.startActivity(intent);

            }});
        textViewMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  Motorista motorista3 = motorista1.get(position);
                Intent intent = new Intent(context,UpdateMotoristaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("edit_motorista",motorista3);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });





            return listViewItem;
    }



}
