package com.example.lahay.comprar;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lahay.Login;
import com.example.lahay.MainActivity;
import com.example.lahay.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComprarFragment extends Fragment {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Comprar> list;
    MyAdapter adapter;
    View view;
    Bitmap bitmapImage;


    public ComprarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_comprar, container, false);
        }
        recyclerView =  view.findViewById(R.id.myRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                   Comprar comprar = dataSnapshot1.getValue(Comprar.class);

                    StorageReference storageRef = null;
                    storageRef = FirebaseStorage.getInstance().getReference();

                    String nomeImagem = comprar.getModeloCarro().replaceAll("\\s+","") + ".jpg";

                    StorageReference islandRef = storageRef.child("images/" + nomeImagem);

                    final long ONE_MEGABYTE = 1024 * 1024;
                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errorso
                        }
                    });

                   comprar.setFotoCarrro(bitmapImage);
                   list.add(comprar);
                }
                adapter = new MyAdapter(getActivity(),list);
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
