package com.example.lahay.comprar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lahay.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList <Comprar> comprar;

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
        myViewHolder.modelo.setText(comprar.get(i).getModelo());
        myViewHolder.descricao.setText(comprar.get(i).getDescricao());
        myViewHolder.preco.setText(comprar.get(i).getPreco());
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
