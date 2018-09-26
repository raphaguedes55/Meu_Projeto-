package com.example.notebookdell.controlenutricional.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
 * A placeholder fragment containing a simple view.
 */
public class InicioFragment extends Fragment {
    private CardView cardReceita,cardChat,cardAcompanhamento,cardSobre;
    private LinearLayout linearPerfil;
    private TextView textPerfil,textNome;
    private FirebaseAuth auth;
    private Query queryPerfil;
    private String idusuario;
    private FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser() ;


    public InicioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        linearPerfil =view.findViewById(R.id.linear_perfil);
        textPerfil =view.findViewById(R.id.text_perfil);
        textNome=view.findViewById(R.id.text_viewUsuario);
        listaid(view);

        auth=FirebaseAuth.getInstance();
        verificaAuth();


        return (view);    }






    private void listaid(View view) {
       cardReceita =view.findViewById(R.id.cardView_sobre);
        cardChat=view.findViewById(R.id.cardView_Chat);
        cardAcompanhamento=view.findViewById(R.id.cardView_acompanhamento);
        cardChat=view.findViewById(R.id.cardView_Chat);
//        cardSobre=view.findViewById(R.id.cardView_sobre);
        linearPerfil =view.findViewById(R.id.linear_perfil);

        cardReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtils.replace(getActivity(), new ReceitasFragment());

            }
        });

        cardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtils.replace(getActivity(),new LoginFragment());
            }
        });
    }

    public void verificaAuth() {

        if (auth.getCurrentUser() != null) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
              linearPerfil.setVisibility(View.VISIBLE);
              textPerfil.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      getUsuario(usuario.getUid());
                      FragmentUtils.replace(getActivity(),new PerfilFragment());
                  }
              });
            }

        }
    }

        private void getUsuario(String uid){
            queryPerfil = DatabaseDAO.getQuerryUsuario(uid);
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
                    textNome.setText("Olá ! "+usuario.getNomeUsuario());
                    Toast.makeText(getContext(),usuario.getNomeUsuario(),Toast.LENGTH_LONG).show();
                }


            }

        }


}
