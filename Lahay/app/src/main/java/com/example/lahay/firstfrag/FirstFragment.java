package com.example.lahay.firstfrag;


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
import com.example.lahay.comprar.MyAdapter;
import com.example.lahay.gerenciar.AdapterGerenciar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    View view;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Comprar> list;
    ArrayList<Comprar> listRandom;
    MyAdapter adapter;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_first, container, false);
        }

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Olá, " + user.getDisplayName() + ".");

        recyclerView =  view.findViewById(R.id.myRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView =  view.findViewById(R.id.myRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        listRandom = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    for(DataSnapshot dataSnapshotCarro: dataSnapshot1.child("Carros").getChildren()){

                        Comprar comprar = dataSnapshotCarro.getValue(Comprar.class);

                        list.add(comprar);
                    }
                }
                Collections.shuffle(list);
                listRandom.add(list.get(0));
                listRandom.add(list.get(1));
                listRandom.add(list.get(2));
                adapter = new MyAdapter(getActivity(),listRandom);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
