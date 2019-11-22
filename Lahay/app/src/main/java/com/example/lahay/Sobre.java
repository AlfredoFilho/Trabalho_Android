package com.example.lahay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sobre extends Fragment {

    View view;

    public Sobre() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_sobre, container, false);
        }

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Sobre");


        return view;
    }

}
