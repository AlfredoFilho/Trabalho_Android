package com.example.lahay.comprar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lahay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesCarro extends Fragment {

    View view;

    public DetalhesCarro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_detalhes_carro, container, false);
        // Inflate the layout for this fragment
        return view;
    }

}
