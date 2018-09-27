package com.example.notebookdell.controlenutricional.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.notebookdell.controlenutricional.R;
import com.example.notebookdell.controlenutricional.Utils.ConstantsUtils;
import com.example.notebookdell.controlenutricional.Utils.FragmentUtils;
import com.example.notebookdell.controlenutricional.model.Receita;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ExibicaoReceitaFragment extends Fragment {
    public static final String URL_IMAGEM = "com.example.notebookdell.controlenutricional.fragments";
    private Bundle bundle;
    private Receita receita;
    private ImageView imageView;
    private TextView textNomeReceita, textIngrediente, textModoPreparo,textTempoPreparo;


    public ExibicaoReceitaFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exibicao, container ,false);
        idCampo(view);
        initView(view);

        return view;
    }

    private void idCampo(View view) {
        textNomeReceita= view.findViewById(R.id.txt_nome_receita);
        textIngrediente=view.findViewById(R.id.txt_nome_igredientes);
        textModoPreparo=view.findViewById(R.id.txt_nome_preparo);
        textTempoPreparo=view.findViewById(R.id.txt_tempo_preparo);
        imageView=view.findViewById(R.id.img_receita);
    }
    private void initView(final View view) {

        bundle = getArguments();
        if (bundle != null) {
            receita = (Receita) bundle.getSerializable(ConstantsUtils.RECEITA);
            textNomeReceita.setText(receita.getNome());
            textIngrediente.setText(receita.getIngredientes());
            textModoPreparo.setText(receita.getPreparo());
            textTempoPreparo.setText(receita.getTempo());
            String url = receita.getImagem1().getUrl();

            try {
                Glide.with(getActivity())
                        .load(url)
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public ExibicaoReceitaFragment newInstance(Receita receita) {
        ExibicaoReceitaFragment exibicaoReceitaFragment = new ExibicaoReceitaFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantsUtils.RECEITA, receita);
        exibicaoReceitaFragment.setArguments(bundle);
        return exibicaoReceitaFragment;
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
                    FragmentUtils.replace(getActivity(), new ReceitasFragment());
                    return true;
                }
                return false;
            }
        });
    }
}
