package com.example.lahay.carrinho;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lahay.MainActivity;
import com.example.lahay.R;
import com.example.lahay.carrinho.AdapterCarrinho.AdapterCarrinho;
import com.example.lahay.comprar.Comprar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarrinhoFragment extends Fragment {

    View view;
    RecyclerView recycleCarrinho;
    ArrayList<Comprar> listaCompras;
    TextView txtTotal;
    Button btnVoltar;
    Button btnFinalizar;
    AdapterCarrinho adapterCarrinho;

    public CarrinhoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_carrinho, container, false);
        }

        Float totalCarrinho = 0.f;

        recycleCarrinho =  view.findViewById(R.id.recycleCarrinho);
        recycleCarrinho.setLayoutManager(new LinearLayoutManager(getActivity()));

        txtTotal = view.findViewById(R.id.txtTotal);
        btnVoltar = view.findViewById(R.id.btnVoltar);
        btnFinalizar = view.findViewById(R.id.btnFinalizar);

        listaCompras = ((MainActivity)getActivity()).getListaCarrinho();

        adapterCarrinho = new AdapterCarrinho(getActivity(), listaCompras);
        recycleCarrinho.setAdapter(adapterCarrinho);

        for (Comprar itemLista: listaCompras) {
            totalCarrinho = totalCarrinho + Float.parseFloat(itemLista.getPreco());
        }

        txtTotal.setText("R$ " + Float.toString(totalCarrinho) + "00");

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fecharCompra;
                fecharCompra = new FecharCompra();
                ((MainActivity)getActivity()).replaceFragment(fecharCompra, "fechar_fragment");
            }
        });


        return view;
    }

}
