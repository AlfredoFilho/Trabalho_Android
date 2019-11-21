package com.example.lahay.carrinho.AdapterCarrinho;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lahay.MainActivity;
import com.example.lahay.R;
import com.example.lahay.comprar.Comprar;

import java.util.ArrayList;

public class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.MyViewHolder> {

    Context context;
    ArrayList<Comprar> comprar;

    public AdapterCarrinho(Context c, ArrayList<Comprar> co){
        this.context = c;
        this.comprar = co;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_carrinho, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.modeloCarro.setText(comprar.get(position).getModeloCarro());
        myViewHolder.precoCarro.setText("R$ " + comprar.get(position).getPreco());

        myViewHolder.onClick(position);

    }

    @Override
    public int getItemCount() {
        return comprar.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView modeloCarro, precoCarro;
        Button btnDetalhes;

        public MyViewHolder (View itemView) {
            super(itemView);

            modeloCarro = (TextView) itemView.findViewById(R.id.modeloCarro);
            precoCarro = (TextView) itemView.findViewById(R.id.precoCarro);
            btnDetalhes = (Button) itemView.findViewById(R.id.btnDetalhes);

        }

        public void onClick(final int position) {
            btnDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                    System.out.println(comprar.get(position).getModeloCarro());

                    ((MainActivity)context).setCarroDetalhes(comprar.get(position));

                    Fragment detalhesCompra;
                    detalhesCompra = new DetalhesCompra();
                    ((MainActivity)context).replaceFragment(detalhesCompra, "cetalhesCompra_fragment");
                }
            });
        }
    }
}
