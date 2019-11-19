package com.example.lahay.carrinho;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lahay.MainActivity;
import com.example.lahay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarrinhoFragment extends Fragment {

    View view;

    public CarrinhoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_carrinho, container, false);
        }

        view.findViewById(R.id.btnFinalizar).setOnClickListener(new View.OnClickListener() {
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
