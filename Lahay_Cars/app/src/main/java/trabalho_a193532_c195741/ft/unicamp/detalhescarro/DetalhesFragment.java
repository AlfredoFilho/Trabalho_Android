package trabalho_a193532_c195741.ft.unicamp.detalhescarro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import trabalho_a193532_c195741.ft.unicamp.R;
import trabalho_a193532_c195741.ft.unicamp.carros.Carro;
import trabalho_a193532_c195741.ft.unicamp.carros.Carros;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesFragment extends Fragment {
    private View view;

    private ImageView imageView;
    private TextView nomeTextView;
    private TextView descricaoTextView;

    private int position;


    public DetalhesFragment() {
        this.position = 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_detalhes, container, false);
        imageView = view.findViewById(R.id.imagem);
        nomeTextView = view.findViewById(R.id.modeloCarro);
        descricaoTextView = view.findViewById(R.id.descricao);

        view.findViewById(R.id.button_anterior).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    position--;
                    mostrarCarro();
                }
            }

        });

        view.findViewById(R.id.button_proximo).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < Carros.carros.length - 1) {
                    position++;
                    mostrarCarro();
                }
            }
        });
        ((Button) view.findViewById(R.id.button_anterior)).setText("<");
        ((Button) view.findViewById(R.id.button_proximo)).setText(">");

        mostrarCarro();
        return view;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void mostrarCarro() {
        Carro carro = Carros.carros[position];
        //imageView.setImageResource(carro.getFoto());
        descricaoTextView.setText(Html.fromHtml(carro.getDescricao()));
    }

}
