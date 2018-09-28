package com.example.notebookdell.controlenutricional.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.notebookdell.controlenutricional.R;
import com.example.notebookdell.controlenutricional.Utils.ConfiguraçõesFirebase;
import com.example.notebookdell.controlenutricional.Utils.FragmentUtils;
import com.example.notebookdell.controlenutricional.adapter.ReceitaAdapter;
import com.example.notebookdell.controlenutricional.model.Receita;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReceitasFragment extends android.support.v4.app.Fragment {
    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private List<Receita> receitas;
    private Query databaseReceita;
    private ReceitaAdapter adapter;
    private ImageView imageReceita;
    private Uri mImageUri;



public ReceitasFragment(){}


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_receita,container,false);
        getActivity().setTitle("Lista de Receitas");
        listaid(view);
        preencherLista();




       return view;

    }

    private void listaid(View view){
        recyclerView = view.findViewById(R.id.recyclerReceita);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void preencherLista() {
        receitas = new ArrayList<>();
        databaseReceita = ConfiguraçõesFirebase.getReceitas().orderByValue();
        databaseReceita.keepSynced(true);
        databaseReceita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try{

                    receitas.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Receita receita = snapshot.getValue(Receita.class);
                        receitas.add(receita);


                    }
                    adapter.atualiza(receitas);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new ReceitaAdapter(getActivity(),receitas);
        recyclerView.setAdapter(adapter);

    }



    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    FragmentUtils.replace(getActivity(), new InicioFragment());
                    return true;
                }
                return false;
            }
        });
    }

}