package trabalho_a193532_c195741.ft.unicamp.carros;

import trabalho_a193532_c195741.ft.unicamp.R;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<Carro> carros;
    private MyOnItemClickListener myOnItemClickListener;

    public interface MyOnItemClickListener {
        void mostrarNomeCarro(String nome);
        void enviarPosicaoCarro(int index);
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public MyAdapter(ArrayList<Carro> carros, Context context) {
        this.carros = carros;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout, parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myOnItemClickListener != null) {
                    TextView txt = view.findViewById(R.id.modeloCarro);
                    myOnItemClickListener.mostrarNomeCarro(txt.getText().toString());
                }
            }

        });


        final MyFirstViewHolder viewHolder = new MyFirstViewHolder(view);
        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                if (myOnItemClickListener != null) {
                    myOnItemClickListener.enviarPosicaoCarro(viewHolder.position);
                    return true;
                }
                return false;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyFirstViewHolder) holder).onBind(carros.get(position), position);
    }

    @Override
    public int getItemCount() {
        return carros.size();
    }


    private class MyFirstViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nomeTextView;
        private TextView descricaoTextView;
        private TextView precoTextView;
        private Button button;
        public int position;

        public MyFirstViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagem);
            nomeTextView = itemView.findViewById(R.id.modeloCarro);
            descricaoTextView = itemView.findViewById(R.id.descricao);
            precoTextView = itemView.findViewById(R.id.preco);
            button = itemView.findViewById(R.id.btnMais);
        }

        public void onBind(final Carro carro, final int position) {
            this.position = position;
            imageView.setImageResource(carro.getFoto());
            precoTextView.setText("R$"+carro.getPreco());
            descricaoTextView.setText(Html.fromHtml(carro.getDescricao()));

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetalhesActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
}



