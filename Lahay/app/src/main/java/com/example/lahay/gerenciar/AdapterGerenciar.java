package com.example.lahay.gerenciar;

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

public class AdapterGerenciar extends RecyclerView.Adapter<AdapterGerenciar.MyViewHolder> {

    Context context;
    ArrayList<Comprar> comprar;

    public AdapterGerenciar(Context c, ArrayList<Comprar> co){
        this.context = c;
        this.comprar = co;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.itensgerenciar, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.modeloCarro.setText(comprar.get(position).getModeloCarro());

        myViewHolder.onClick(position);

    }

    @Override
    public int getItemCount() {
        return comprar.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView modeloCarro;
        Button btnGerenciar;

        public MyViewHolder (View itemView) {
            super(itemView);

            modeloCarro = (TextView) itemView.findViewById(R.id.modeloCarro);
            btnGerenciar = (Button) itemView.findViewById(R.id.btnGerenciar);

        }

        public void onClick(final int position) {
            btnGerenciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).setCarroDetalhes(comprar.get(position));

                    Fragment detalhesGerenciar;
                    detalhesGerenciar = new DetalhesGerenciar();
                    ((MainActivity)context).replaceFragment(detalhesGerenciar, "detalhesGerenciar_fragment");
                }
            });
        }
    }
}
