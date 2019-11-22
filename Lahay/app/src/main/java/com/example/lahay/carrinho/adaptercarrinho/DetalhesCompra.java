package com.example.lahay.carrinho.adaptercarrinho;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lahay.MainActivity;
import com.example.lahay.R;
import com.example.lahay.comprar.Comprar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesCompra extends Fragment {

    View view;
    Comprar carroDetalhes;
    TextView modeloCarro;
    ImageView imageCarro;
    TextView modeloCarro2;
    TextView anoCarro;
    TextView cambioCarro;
    TextView corCarro;
    TextView descricaoCarro;
    TextView estiloCarro;
    TextView precoCarro;
    Button btnRemover;
    ArrayList<Comprar> listaCompras;

    public DetalhesCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_detalhes_compra, container, false);
        }

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Detalhes");

        carroDetalhes = ((MainActivity)getActivity()).getCarroDetalhes();
        listaCompras = ((MainActivity)getActivity()).getListaCarrinho();

        modeloCarro = view.findViewById(R.id.modeloCarro);
        imageCarro = view.findViewById(R.id.imagemCarro);
        modeloCarro2 = view.findViewById(R.id.modeloCarro2);
        anoCarro = view.findViewById(R.id.anoCarro);
        cambioCarro = view.findViewById(R.id.cambioCarro);
        corCarro = view.findViewById(R.id.corCarro);
        estiloCarro = view.findViewById(R.id.estiloCarro);
        descricaoCarro = view.findViewById(R.id.descricaoCarro);
        precoCarro = view.findViewById(R.id.precoCarro);
        btnRemover = view.findViewById(R.id.btnRemover);

        modeloCarro.setText(carroDetalhes.getModeloCarro());
        modeloCarro2.setText(carroDetalhes.getModeloCarro());
        anoCarro.setText(carroDetalhes.getAno());
        cambioCarro.setText(carroDetalhes.getCambio());
        corCarro.setText(carroDetalhes.getCor());
        estiloCarro.setText(carroDetalhes.getEstilo());
        descricaoCarro.setText(carroDetalhes.getDescricao());
        precoCarro.setText("R$ " + carroDetalhes.getPreco());

        StorageReference storageRef = null;
        storageRef = FirebaseStorage.getInstance().getReference();

        String nomeImagem = carroDetalhes.getModeloCarro().replaceAll("\\s+","") + ".jpg";

        StorageReference islandRef = storageRef.child("images/" + nomeImagem);

        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Picasso.get().load(uri.toString()).into(imageCarro);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Item removido", Toast.LENGTH_SHORT).show();
                removerDaLista();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    public void removerDaLista(){

        int cont = 0;

        for (Comprar itemLista: listaCompras) {

            if(itemLista.getModeloCarro().equals(carroDetalhes.getModeloCarro())){

                listaCompras.remove(itemLista);
                break;
            }
            cont = cont + 1;

        }

        ((MainActivity)getActivity()).setListaCarrinho(listaCompras);

    }

}
