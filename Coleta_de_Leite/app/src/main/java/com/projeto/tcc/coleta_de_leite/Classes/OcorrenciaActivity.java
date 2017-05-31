package com.projeto.tcc.coleta_de_leite.Classes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projeto.tcc.coleta_de_leite.R;

/**
 * Created by raphael on 31/05/17.
 */

public class OcorrenciaActivity extends AppCompatActivity {
    ImageView imageView;
    private static final int CAMERA_REQUESTE_CODE=1;
    private StorageReference mStorage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencia);
        imageView = (ImageView) findViewById(R.id.foto);
        mStorage= FirebaseStorage.getInstance().getReference();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,CAMERA_REQUESTE_CODE);

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUESTE_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference arquivo = mStorage.child("foto").child(uri.getLastPathSegment());

        }
    }
}
