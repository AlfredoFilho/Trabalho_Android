package com.example.lahay.gerenciar;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.lahay.comprar.MyAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyAsyncTaskGerenciar extends AsyncTask<String, Void, ImageView> {
    ImageView fotoCarro;
    EditarCarro fragment;


    public MyAsyncTaskGerenciar(EditarCarro fragment, ImageView fotoCarro) {
        this.fotoCarro = fotoCarro;
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ImageView doInBackground(String... args){

        StorageReference storageRef = null;
        storageRef = FirebaseStorage.getInstance().getReference();

        String nomeImagem = args[0].replaceAll("\\s+","") + ".jpg";

        StorageReference islandRef = storageRef.child("images/" + nomeImagem);

        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Picasso.get().load(uri.toString()).into(fotoCarro);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return fotoCarro;

    }

    @Override
    protected void onPostExecute(ImageView fotoCarro) {

    }
}