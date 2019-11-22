package com.example.lahay.gerenciar;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lahay.MainActivity;
import com.example.lahay.R;
import com.example.lahay.comprar.Comprar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GerenciarVendas extends Fragment {

    View view;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Comprar> list;
    AdapterGerenciar adapter;


    public GerenciarVendas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_gerenciar_vendas, container, false);
        }

        recyclerView =  view.findViewById(R.id.myRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Gerenciar Vendas");

        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(user.getUid());
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    for(DataSnapshot dataSnapshotCarro: dataSnapshot1.getChildren()){

                        Comprar comprar = dataSnapshotCarro.getValue(Comprar.class);
                        list.add(comprar);
                    }

                }
                adapter = new AdapterGerenciar(getActivity(), list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Text!", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }

}
