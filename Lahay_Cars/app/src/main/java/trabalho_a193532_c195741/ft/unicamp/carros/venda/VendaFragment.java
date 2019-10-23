package trabalho_a193532_c195741.ft.unicamp.carros.venda;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import trabalho_a193532_c195741.ft.unicamp.R;

import static android.app.Activity.RESULT_OK;

public class VendaFragment extends Fragment {

    private Button cancelarBtnVenda;
    private Button carregarFoto;
    private View view;
    private ImageView fotocarro;
    private final int GALERIA_IMAGENS = 1;

    public VendaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_venda, container, false);

            cancelarBtnVenda = (Button) view.findViewById(R.id.CancelarVenda);
            fotocarro = (ImageView) view.findViewById(R.id.ImgCarro);
            carregarFoto = (Button) view.findViewById(R.id.btnImagem);


            cancelarBtnVenda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().onBackPressed();
                }
            });

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
        }

        return view;
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
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fotocarro.setImageBitmap(bitmapImage);
            }
        }
        //Uri returnUri;
        //returnUri = data.getData();
    }
}