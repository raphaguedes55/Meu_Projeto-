package com.example.notebookdell.controlenutricional.Utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageUtis {

    public static byte[] imageViewParaBytes(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageView.setDrawingCacheEnabled(false);
        return  baos.toByteArray();


    }
}