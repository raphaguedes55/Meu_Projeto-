package com.example.notebookdell.controlenutricional.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notebookdell.controlenutricional.DAO.DatabaseDAO;
import com.example.notebookdell.controlenutricional.R;
import com.example.notebookdell.controlenutricional.Utils.FragmentUtils;
import com.example.notebookdell.controlenutricional.model.Usuario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroFragment extends Fragment {

    private EditText datanNascimento;
    private EditText nomeUsuario;
    private EditText telefone;
    private EditText cadastro_email;
    private EditText senha1,senha2;
    private Button registrar;
    private FirebaseAuth auth;

    public CadastroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        auth = FirebaseAuth.getInstance();

        listaid(view);

        SimpleMaskFormatter data = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher data1 = new MaskTextWatcher(datanNascimento, data);
        datanNascimento.addTextChangedListener(data1);

        SimpleMaskFormatter celular = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher cel = new MaskTextWatcher(telefone, celular);
        telefone.addTextChangedListener(cel);

        return view;
    }

    private void listaid(View view) {
        nomeUsuario=view.findViewById(R.id.editText_cadastro_nome);
        datanNascimento =view.findViewById(R.id.editText_cadastro_data_nascimento);
        telefone =view.findViewById(R.id.editText_cadastro_numero_celular);
        cadastro_email=view.findViewById(R.id.editText_cadastro_email);
        senha1=view.findViewById(R.id.editText_cadastro_senha);
        senha2=view.findViewById(R.id.editText_cadastro_confirmar_senha);
        registrar=view.findViewById(R.id.cadastrar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrologar();
            }
        });

    }

    private void cadastrologar() {

        final String nome = nomeUsuario.getText().toString().trim();
        final String nascimento = datanNascimento.getText().toString().trim();
        final String numero = telefone.getText().toString().trim();
        final String email = cadastro_email.getText().toString().trim();
        final String senha = senha1.getText().toString().trim();


        if (TextUtils.isEmpty(nome)) {
            nomeUsuario.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(nascimento)) {
            datanNascimento.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(numero)) {
            telefone.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(email)) {
            cadastro_email.setError("Campo Obrigatório");


            return;
        }

        if (senha.length() < 6) {
            senha1.setError("Campo Obrigatório");
            return;
        }


        registerUser(nome,nascimento,numero,email,senha);

    }

    private void registerUser(final String nome ,final String data,final String numero,final String email,final String password) {

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String token = user.getUid();
                            Usuario usuario = new Usuario();
                            usuario.setId(token);
                            usuario.setNomeUsuario(nome);
                            usuario.setDataNascimento(data);
                            usuario.setNumeroCelular(numero);
                            usuario.setEmail(email);
                            usuario.setSenha(password);
                            usuario.setRoot(false);

                            new DatabaseDAO().saveUsuario(usuario);
                            FragmentUtils.replace(getActivity(), new InicioFragment());

                        } else {
                            Log.e("Algo deu Errado","");
                        }

                    }

                });

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
                    FragmentUtils.replace(getActivity(), new LoginFragment());
                    return true;
                }
                return false;
            }
        });
    }

}