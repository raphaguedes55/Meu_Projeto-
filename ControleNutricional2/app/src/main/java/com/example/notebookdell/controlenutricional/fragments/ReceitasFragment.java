package com.example.notebookdell.controlenutricional.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notebookdell.controlenutricional.R;
import com.example.notebookdell.controlenutricional.Utils.FragmentUtils;

public class ReceitasFragment extends android.support.v4.app.Fragment {
    FloatingActionButton floatingActionButton;



public ReceitasFragment(){}


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_receita,container,false);
        getActivity().setTitle("Lista de Receitas");
        listaid(view);




       return view;

    }

    private void listaid(View view){
    floatingActionButton = view.findViewById(R.id.fab_adc_receita);
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentUtils.replace(getActivity(), new NovasReceitasFragment());
        }
    });
    }
}