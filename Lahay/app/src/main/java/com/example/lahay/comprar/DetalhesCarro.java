package com.example.lahay.comprar;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lahay.MainActivity;
import com.example.lahay.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesCarro extends Fragment {

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
    Button btnVoltar;
    Button btnComprar;

    public DetalhesCarro() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_detalhes_carro, container, false);

        carroDetalhes = ((MainActivity)getActivity()).getCarroDetalhes();

        modeloCarro = view.findViewById(R.id.modeloCarro);
        imageCarro = view.findViewById(R.id.imagemCarro);
        modeloCarro2 = view.findViewById(R.id.modeloCarro2);
        anoCarro = view.findViewById(R.id.anoCarro);
        cambioCarro = view.findViewById(R.id.cambioCarro);
        corCarro = view.findViewById(R.id.corCarro);
        estiloCarro = view.findViewById(R.id.estiloCarro);
        descricaoCarro = view.findViewById(R.id.descricaoCarro);
        precoCarro = view.findViewById(R.id.precoCarro);
        btnVoltar = view.findViewById(R.id.btnVoltar);
        btnComprar = view.findViewById(R.id.btnComprar);

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

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).adicionarCarrinho(carroDetalhes);
                Toast.makeText(getActivity(), "Adicionado ao Carrinho", Toast.LENGTH_SHORT).show();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().onBackPressed();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

}
