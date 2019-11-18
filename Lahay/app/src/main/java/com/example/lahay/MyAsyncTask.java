package com.example.lahay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView fotoCarro;

    public MyAsyncTask(ImageView fotoCarro) {
        this.fotoCarro = fotoCarro;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Bitmap doInBackground(String... args) {


        final Bitmap[] bitmapImage = new Bitmap[1];
        StorageReference storageRef = null;
        storageRef = FirebaseStorage.getInstance().getReference();

        String nomeImagem = args[0].replaceAll("\\s+","") + ".jpg";

        StorageReference islandRef = storageRef.child("images/" + nomeImagem);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmapImage[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errorso
            }
        });

        while (bitmapImage[0] == null) {
            continue;
        }

        return bitmapImage[0];

    }

    @Override
    protected void onPostExecute(Bitmap bitmapImage) {
        fotoCarro.setImageBitmap(bitmapImage);

    }
}