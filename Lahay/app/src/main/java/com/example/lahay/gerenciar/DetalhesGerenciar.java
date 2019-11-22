package com.example.lahay.gerenciar;


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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesGerenciar extends Fragment {

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
    Button btnEditar;
    Button btnRemover;
    ArrayList<Comprar> listaCompras;
    DatabaseReference reference;

    public DetalhesGerenciar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_detalhes_gerenciar, container, false);
        }

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Detalhes");

        reference = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        btnEditar = view.findViewById(R.id.btnEditar);
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

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment editarCarro;
                editarCarro = new EditarCarro();
                ((MainActivity)getActivity()).replaceFragment(editarCarro, "editarCarro_fragment");

            }
        });

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("Users").child(user.getUid()).child("Carros").child(carroDetalhes.getModeloCarro()).removeValue();
                Toast.makeText(getActivity(), "Veículo removido", Toast.LENGTH_SHORT).show();
                fragmentManager.popBackStack();
            }
        });

        return view;

    }

}
