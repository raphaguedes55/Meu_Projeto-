package com.example.notebookdell.controlenutricional.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.notebookdell.controlenutricional.R;
import com.example.notebookdell.controlenutricional.Utils.FragmentUtils;
import com.example.notebookdell.controlenutricional.fragments.ExibicaoReceitaFragment;
import com.example.notebookdell.controlenutricional.model.Receita;

import java.util.Collections;
import java.util.List;


public class ReceitaAdapter extends RecyclerView.Adapter<ReceitaAdapter.ViewHolder>{
    private FragmentActivity activity;
    private List<Receita> receitas;




    public ReceitaAdapter(FragmentActivity activity , List<Receita> receitas){
        this.activity=activity;
        this.receitas=receitas;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageReceita;
        private LinearLayout linear_receita;
        private TextView textNomeReceita;


        public ViewHolder(View itemView) {
            super(itemView);
           imageReceita= itemView.findViewById(R.id.imagem);
           linear_receita = itemView.findViewById(R.id.linearAdapter);
           textNomeReceita = itemView.findViewById(R.id.txt_nome_receita);
        }
    }
    public void atualiza(List<Receita> produtos){
        Collections.reverse(produtos);
        this.receitas=receitas;
        this.notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ReceitaAdapter.ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceitaAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_receita,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Receita receita = receitas.get(position);

        try {
            Glide.with(activity).load(receita.getImagem1().getUrl()).apply(RequestOptions.centerCropTransform()).into(holder.imageReceita);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.textNomeReceita.setText(receita.getNome());
        holder.linear_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtils.replace(activity, new ExibicaoReceitaFragment().newInstance(receita));
            }
        });

    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }



 }



