package com.example.notebookdell.controlenutricional.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.notebookdell.controlenutricional.DAO.DatabaseDAO;
import com.example.notebookdell.controlenutricional.R;
import com.example.notebookdell.controlenutricional.model.Receita;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.SimpleDateFormat;

import static android.app.Activity.RESULT_OK;

public class NovasReceitasFragment extends Fragment {
    private EditText edit_nome , edit_ingredientes, edit_preparo,edit_tempo;
    private ImageView image1, image2, image3;
    private Receita receita =  new Receita();
    private Spinner categoria;
    private String data;
    private Button salvar;
    private int imgI = 0;
    FirebaseStorage storage;
    StorageReference storageReference;
    private boolean aBoolean = false;


    public NovasReceitasFragment (){};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_novas_receitas,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        idCampo(view);
        setaBackGround(image1,image2,image3);



        return view;

    }

    private void idCampo(View view) {

        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);

        edit_nome = view.findViewById(R.id.edit_nome_receita);
        edit_ingredientes = view.findViewById(R.id.edit_ingredientes);
        edit_preparo = view.findViewById(R.id.edit_modo_preparo);
        categoria = view.findViewById(R.id.spinner_categoria);
        edit_tempo = view.findViewById(R.id.edit_tempo);
        salvar= view.findViewById(R.id.btn_salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarcampos();
            }
        });

        long date = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        data = formatter.format(date);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(1);


            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(2);
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(3);

            }

        });

    }


    private void setaBackGround(ImageView image1, ImageView image2, ImageView image3) {
        Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.def)).into(image1);

        Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.def)).into(image2);

        Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.def)).into(image3);

    }
    private void uploadImage() {

        if (aBoolean) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(getActivity().getString(R.string.aguarde));
            progressDialog.show();
            receita.setNome(edit_nome.getText().toString());
            receita.setIngredientes(edit_ingredientes.getText().toString());
            receita.setPreparo(edit_preparo.getText().toString());
            receita.setTempo(edit_tempo.getText().toString());
            receita.setCategoria(categoria.getSelectedItem().toString());


            new DatabaseDAO().uploadDados(getActivity(), image1, image2, image3, receita, progressDialog);

        } else {
            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());

            alert
                    .setTitle("Ops!")
                    .setIcon(R.drawable.crop__ic_done)
                    .setMessage(" voce precisa inserir uma foto para publicar a receita")
                    .setCancelable(true)
                    .setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }

                    });

            android.app.AlertDialog alertDialog = alert.create();
            alertDialog.show();

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());


        } else if (requestCode == Crop.REQUEST_CROP) {
            setImage(resultCode, result);

        }

    }
    private void beginCrop(Uri source) {

        Uri destinarion = Uri.fromFile(new File(getActivity().getCacheDir(), "croped" + String.valueOf(System.currentTimeMillis())));
        Crop.of(source, destinarion).asSquare().start(getContext(), NovasReceitasFragment.this);

    }

    private void setImage(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            aBoolean = true;
            switch (imgI) {
                case 1:
                    Glide.with(getActivity()).load(Crop.getOutput(result)).into(image1);

                    break;

                case 2:
                    Glide.with(getActivity()).load(Crop.getOutput(result)).into(image2);

                    break;
                case 3:
                    Glide.with(getActivity()).load(Crop.getOutput(result)).into(image3);

                    break;
            }


        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void selecionarImagem( int i) {
        imgI = i;
        Crop.pickImage(getContext(), NovasReceitasFragment.this);

    }
    private void validarcampos() {

        final String nome = edit_nome.getText().toString().trim();
        final String ingredientes = edit_ingredientes.getText().toString().trim();
        final String preparo = edit_preparo.getText().toString().trim();
        final String tempo = edit_tempo.getText().toString().trim();


        if (TextUtils.isEmpty(nome)) {
            edit_nome.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(ingredientes)) {
            edit_ingredientes.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(preparo)) {
            edit_preparo.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(tempo)) {
            edit_tempo.setError("Campo Obrigatório");


            return;
        }
        uploadImage();


    }

}
