package com.example.notebookdell.controlenutricional.DAO;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notebookdell.controlenutricional.MainActivity;
import com.example.notebookdell.controlenutricional.R;
import com.example.notebookdell.controlenutricional.Utils.ConfiguraçõesFirebase;
import com.example.notebookdell.controlenutricional.Utils.ConstantsUtils;
import com.example.notebookdell.controlenutricional.Utils.ImageUtis;
import com.example.notebookdell.controlenutricional.model.Imagem;
import com.example.notebookdell.controlenutricional.model.Receita;
import com.example.notebookdell.controlenutricional.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class   DatabaseDAO {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private boolean sucesso = true;
    private int cont = 0;



    public void saveUsuario(Usuario usuario) {
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_USUARIO).child(String.valueOf(usuario.getId())).setValue(usuario);
    }



    public void uploadDados(FragmentActivity activity, ImageView image1, ImageView image2, ImageView image3, Receita receita, ProgressDialog progressDialog) {
        uploadImagem(image1, activity, receita.getImagem1(), receita, progressDialog);
        uploadImagem(image2, activity, receita.getImagem2(), receita, progressDialog);
        uploadImagem(image3, activity, receita.getImagem3(), receita, progressDialog);
        }


    private void uploadReceita(Receita receita) {
        receita.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        Log.v("teste save", receita.getId() + receita.getNome());
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_RECEITAS).child(String.valueOf(receita.getId())).setValue(receita);
    }
    private boolean uploadImagem(final ImageView image, final FragmentActivity activity, final Imagem imagem, final Receita receita, final ProgressDialog progressDialog) {

        byte[] data = ImageUtis.imageViewParaBytes(image);
        final String path = ConstantsUtils.BANCO_RECEITAS + UUID.randomUUID() + ".png";
        imagem.setPath(path);
        final StorageReference reference = storage.getReference(path);


        StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("text", receita.getNome()).build();

        UploadTask uploadTask = reference.putBytes(data, metadata);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                progressDialog.dismiss();
                Toast.makeText(activity, "Erro!:.>" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                sucesso = false;

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                StorageReference storageRef = storage.getReference();
                storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        imagem.setUrl(String.valueOf(uri));
                        cont++;

                        if(cont==3 && imagem.getUrl()!= null){
                            uploadReceita(receita);
                            progressDialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(activity);

                            alert
                                    .setTitle("Nova Receita ;)")
                                    .setIcon(R.drawable.common_google_signin_btn_icon_dark)
                                    .setMessage("Sua receita foi publicada com sucesso!")
                                    .setCancelable(false)
                                    .setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            activity.startActivity(new Intent(activity, MainActivity.class));
                                        }

                                    });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });





            }
        });


        return sucesso;
    }
    public static Query getQuerryUsuario(String uId) {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_USUARIO).child(uId);
    }
}
