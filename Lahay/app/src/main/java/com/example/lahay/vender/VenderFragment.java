package com.example.lahay.vender;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lahay.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class VenderFragment extends Fragment {

    private EditText modeloCarro;
    private RadioGroup radioGroupEstilo;
    private RadioGroup radioGroupCambio;
    private EditText descricaoCarro;
    private Spinner anoCarro;
    private Spinner corCarro;
    private EditText precoCarro;
    private Button carregarFoto;
    private ImageView fotoCarro;
    private View view;
    private Bitmap bitmapImage;



    private Uri mImageUri;
    private DatabaseReference mDatabaseref;
    private StorageReference mstorageRef;



    public VenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        mstorageRef = FirebaseStorage.getInstance().getReference("upload");
        mDatabaseref = FirebaseDatabase.getInstance().getReference("upload");






        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_vender, container, false);
        }

        modeloCarro = view.findViewById(R.id.edModelo);
        radioGroupEstilo = view.findViewById(R.id.groupEstilo);
        radioGroupCambio = view.findViewById(R.id.groupCambio);
        descricaoCarro = view.findViewById(R.id.edDescricao);
        anoCarro = view.findViewById(R.id.spinnerAno);
        corCarro = view.findViewById(R.id.spinnerCor);
        precoCarro = view.findViewById(R.id.edPreco);
        carregarFoto = view.findViewById(R.id.btnFoto);
        fotoCarro = view.findViewById(R.id.fotoCarro);

        carregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                } else {
                    startGallery();
                }
            }
        });

        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.btnCadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarVenda();
            }
        });

        return view;
    }

    public void cadastrarVenda(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseBD = FirebaseDatabase.getInstance();

        RadioButton radioEstiloSelected;
        RadioButton radioCambioSelected;

        int selectedId = radioGroupEstilo.getCheckedRadioButtonId();
        radioEstiloSelected = view.findViewById(selectedId);

        int selectedCambioId = radioGroupCambio.getCheckedRadioButtonId();
        radioCambioSelected = view.findViewById(selectedCambioId);

        VendaData vendaData = new VendaData(
                modeloCarro.getText().toString(),
                radioEstiloSelected.getText().toString(),
                anoCarro.getSelectedItem().toString(),
                corCarro.getSelectedItem().toString(),
                radioCambioSelected.getText().toString(),
                descricaoCarro.getText().toString(),
                precoCarro.getText().toString()
        );

        firebaseBD.getReference().child("Users").child(user.getUid()).push().child("Carros").setValue(vendaData);

        StorageReference storageRef = null;
        storageRef = FirebaseStorage.getInstance().getReference();

        String nomeImagemm = modeloCarro.getText().toString().replaceAll("\\s+","") + ".jpg";

        StorageReference mountainsRef = storageRef.child(nomeImagemm);
        //StorageReference mountainImagesRef = storageRef.child("images/" + nomeImagemm);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

        Toast.makeText(getActivity(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fotoCarro.setImageBitmap(bitmapImage);
            }
        }
    }
}
