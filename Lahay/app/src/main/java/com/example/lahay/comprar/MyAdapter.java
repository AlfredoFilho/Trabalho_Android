package com.example.lahay.comprar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lahay.MainActivity;
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

    public MyAdapter(Context c, ArrayList<Comprar> co){
        this.context = c;
        this.comprar = co;
    }

    public void attFoto(Bitmap fotoCarro, int position){
        comprar.get(position).setFotoCarrro(fotoCarro);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.modelo.setText(comprar.get(position).getModeloCarro());
        myViewHolder.preco.setText("R$ " + comprar.get(position).getPreco());
        new MyAsyncTask(myViewHolder.fotoCarro, this, position).execute(comprar.get(position).getModeloCarro());

        myViewHolder.onClick(position);

    }

    @Override
    public int getItemCount() {
        return comprar.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView modelo, descricao, preco;
        ImageView fotoCarro;
        Button btn;

        public MyViewHolder (View itemView) {
            super(itemView);

            modelo = (TextView) itemView.findViewById(R.id.modeloCarro);
            preco = (TextView) itemView.findViewById(R.id.preco);
            fotoCarro = (ImageView) itemView.findViewById(R.id.imagemCarro);
            btn = (Button) itemView.findViewById(R.id.btnMais);

            Glide.with(context)
                    .load(R.drawable.loadergif) // aqui é teu gif
                    .asGif()
                    .into(fotoCarro);

        }

        public void onClick(final int position) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   ((MainActivity)context).setCarroDetalhes(comprar.get(position));

                    Fragment detalhesCarro;
                    detalhesCarro = new DetalhesCarro();
                    ((MainActivity)context).replaceFragment(detalhesCarro, "detalhesCarro_fragment");
                }
            });
        }
    }
}
