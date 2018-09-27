package com.example.notebookdell.controlenutricional.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notebookdell.controlenutricional.DAO.DatabaseDAO;
import com.example.notebookdell.controlenutricional.R;
import com.example.notebookdell.controlenutricional.Utils.FragmentUtils;
import com.example.notebookdell.controlenutricional.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private FirebaseAuth auth;
    private TextView pessoa, celular, email, leiloes;
    private Query queryPerfil;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            getUsuario(user.getUid());

        }

        setHasOptionsMenu(true);
        pessoa = view.findViewById(R.id.pessoaPerfil);
        celular = view.findViewById(R.id.celPerfil);
        email = view.findViewById(R.id.emailPerfil);

        metodobotoes(view);

        return view;

    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_desconectar, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.desconectarPerfil:
                desconectarPerfil();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void desconectarPerfil() {
                auth.signOut();
                FragmentUtils.replace(getActivity(), new InicioFragment());

    }

    public void  metodobotoes (View view){


    }

    private void getUsuario(String uId){
        queryPerfil = DatabaseDAO.getQuerryUsuario(uId);
        queryPerfil.keepSynced(true);
        queryPerfil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    exibir(usuario);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void exibir(Usuario usuario) {

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            if (user.getDisplayName() != null) {
                pessoa.setText(usuario.getNomeUsuario());
                Log.d("Foi usuario ",usuario.getNomeUsuario());
            }
            if (user.getPhoneNumber() != null){
                celular.setText(usuario.getNumeroCelular());
            }
            if (user.getEmail() != null ) {
                email.setText(usuario.getEmail());
            }

        }

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
