package trabalho_a193532_c195741.ft.unicamp.carros;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import trabalho_a193532_c195741.ft.unicamp.CestaDeCompras;
import trabalho_a193532_c195741.ft.unicamp.R;

public class DetalhesActivity extends AppCompatActivity {


    private int position;
    private Context context;
    private Carro carro;
    private ImageView imagem;
    private TextView modelo;
    private TextView ano;
    private TextView preco;
    private TextView descricao;
    private TextView cor;
    private TextView cambio;
    private TextView outro;
    private Button cancelarBtn;
    private Button comprarCarro;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent in = getIntent();
        if (in != null) {
            this.position = in.getIntExtra("position", 0);

        }

        this.carro = Carros.carros[this.position];

        imagem = findViewById(R.id.imagemCarro);
        imagem.setImageResource(carro.getFoto());

        modelo = findViewById(R.id.modeloCarro);
        modelo.setText(carro.getModelo());

        outro = findViewById(R.id.outroCarro);
        outro.setText(carro.getAno());

        cor = findViewById(R.id.corCarro);
        cor.setText(carro.getCor());

        preco = findViewById(R.id.precoCarro);
        preco.setText("R$ "+carro.getPreco());

        descricao = findViewById(R.id.descricaoCarro);
        descricao.setText(carro.getDescricao());

        cambio = findViewById(R.id.cambioCarro);
        cambio.setText(carro.getCambio());

        cancelarBtn = findViewById(R.id.cancelarBtn);
        comprarCarro = findViewById(R.id.comprarCarro);

        comprarCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancelarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Toast.makeText(this, "Posicao clicada = " + this.position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
