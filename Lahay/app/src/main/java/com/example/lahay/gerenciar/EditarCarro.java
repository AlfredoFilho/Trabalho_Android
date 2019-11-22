package com.example.lahay.gerenciar;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lahay.MainActivity;
import com.example.lahay.MyAsyncTask;
import com.example.lahay.R;
import com.example.lahay.comprar.Comprar;
import com.example.lahay.vender.VendaData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarCarro extends Fragment {

    View view;
    private EditText modeloCarro;
    private RadioGroup radioGroupEstilo;
    private RadioGroup radioGroupCambio;
    private EditText descricaoCarro;
    private Spinner anoCarro;
    private Spinner corCarro;
    private EditText precoCarro;
    private Button carregarFoto;
    private ImageView fotoCarro;
    private Bitmap bitmapImage;
    private DatabaseReference mDatabaseref;
    private StorageReference mstorageRef;
    Comprar carroDetalhes;
    DatabaseReference reference;

    public EditarCarro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mstorageRef = FirebaseStorage.getInstance().getReference("upload");
        mDatabaseref = FirebaseDatabase.getInstance().getReference("upload");

        if(view == null){
            view = inflater.inflate(R.layout.fragment_editar_carro, container, false);
        }

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Vender");
        carroDetalhes = ((MainActivity)getActivity()).getCarroDetalhes();

        modeloCarro = view.findViewById(R.id.edModelo);
        radioGroupEstilo = view.findViewById(R.id.groupEstilo);
        radioGroupCambio = view.findViewById(R.id.groupCambio);
        descricaoCarro = view.findViewById(R.id.edDescricao);
        anoCarro = view.findViewById(R.id.spinnerAno);
        corCarro = view.findViewById(R.id.spinnerCor);
        precoCarro = view.findViewById(R.id.edPreco);
        carregarFoto = view.findViewById(R.id.btnFoto);
        fotoCarro = view.findViewById(R.id.fotoCarro);

        modeloCarro.setText(carroDetalhes.getModeloCarro());
        descricaoCarro.setText(carroDetalhes.getDescricao());
        precoCarro.setText(carroDetalhes.getPreco());

        StorageReference storageRef = null;
        storageRef = FirebaseStorage.getInstance().getReference();

        String nomeImagem = carroDetalhes.getModeloCarro().replaceAll("\\s+","") + ".jpg";

        StorageReference islandRef = storageRef.child("images/" + nomeImagem);

        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                bitmapImage = getBitmap(getActivity(), uri.toString());
                fotoCarro.setImageBitmap(bitmapImage);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        radioGroupCambio.check(0);
        radioGroupEstilo.check(0);

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

        view.findViewById(R.id.btnEditar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(modeloCarro.getText().toString().isEmpty() || anoCarro.getSelectedItem().toString().isEmpty() ||
                        corCarro.getSelectedItem().toString().isEmpty() || descricaoCarro.getText().toString().isEmpty() ||
                        precoCarro.getText().toString().isEmpty()){

                    Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                }else{
                    editarVenda();
                }
            }
        });

        return view;
    }

    public static Bitmap getBitmap(Context context, String url) {
        final String CACHE_PATH = context.getCacheDir().getAbsolutePath() + "/picasso-cache/"; File[] files=new File(CACHE_PATH).listFiles();
        for (File file:files) { String fname= file.getName();
            if (fname.contains(".") && fname.substring(fname.lastIndexOf(".")).equals(".0")) {
                try { BufferedReader br=new BufferedReader(new FileReader(file));
                    if (br.readLine().equals(url)) {
                        String image_path= CACHE_PATH + fname.replace(".0", ".1");
                        if (new File(image_path).exists()) { return BitmapFactory.decodeFile(image_path); } } }
                        catch (IOException e) { } } } return null; }

    public void editarVenda(){

        reference = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

        firebaseBD.getReference().child("Users").child(user.getUid()).child("Carros").child(modeloCarro.getText().toString()).setValue(vendaData);

        StorageReference storageRef = null;
        storageRef = FirebaseStorage.getInstance().getReference();

        String nomeImagem = modeloCarro.getText().toString().replaceAll("\\s+","") + ".jpg";

        StorageReference mountainsRef = storageRef.child("images/" + nomeImagem);
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

        if(modeloCarro.getText().toString().equals(carroDetalhes.getModeloCarro())){
            ;
        }else{
            reference.child("Users").child(user.getUid()).child("Carros").child(carroDetalhes.getModeloCarro()).removeValue();
        }

        Toast.makeText(getActivity(), "Editado com sucesso!", Toast.LENGTH_SHORT).show();

        Fragment gerenciarVendas;
        gerenciarVendas = new GerenciarVendas();
        ((MainActivity)getActivity()).replaceFragment(gerenciarVendas, "gerenciarVendas_fragment");

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
