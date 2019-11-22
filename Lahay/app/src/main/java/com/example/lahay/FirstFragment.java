package com.example.lahay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    View view;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null){
            inflater.inflate(R.layout.fragment_first, container, false);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Olá, " + user.getDisplayName() + ".");

        return view;
    }

}
