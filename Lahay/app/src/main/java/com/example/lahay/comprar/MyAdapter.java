package com.example.lahay.comprar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lahay.MyAsyncTask;
import com.example.lahay.R;
import com.example.lahay.vender.VenderFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList <Comprar> comprar;
    Bitmap bitmapImage;

    public MyAdapter(Context c, ArrayList<Comprar> co){
        this.context = c;
        this.comprar = co;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        //Bitmap bitmapImage;

        myViewHolder.modelo.setText(comprar.get(i).getModeloCarro());
        myViewHolder.descricao.setText(comprar.get(i).getDescricao());
        myViewHolder.preco.setText(comprar.get(i).getPreco());
        //myViewHolder.fotoCarro.setImageBitmap(comprar.get(i).getFotoCarrro());

        new MyAsyncTask(myViewHolder.fotoCarro).execute(comprar.get(i).getModeloCarro());

    }

    @Override
    public int getItemCount() {
        return comprar.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView modelo, descricao, preco;
        ImageView fotoCarro;
        public MyViewHolder (View itemView){
            super(itemView);
            modelo = (TextView) itemView.findViewById(R.id.modeloCarro);
            descricao = (TextView) itemView.findViewById(R.id.descricao);
            preco = (TextView) itemView.findViewById(R.id.preco);
            fotoCarro = (ImageView) itemView.findViewById(R.id.imagemCarro);

        }
    }
}
