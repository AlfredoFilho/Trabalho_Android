package trabalho_a193532_c195741.ft.unicamp.carros.venda;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import trabalho_a193532_c195741.ft.unicamp.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendaFragment extends Fragment {

    private Button cancelarBtnVenda;
    private View view;
    private ImageView fotocarro;
    private final int GALERIA_IMAGENS = 1;

    public VendaFragment() {
        // Required empty public constructor
    }

    public void setImageGaleria(Bitmap fotoCarro){
        fotocarro.setImageBitmap(fotoCarro);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

     if (view == null){
         view = inflater.inflate(R.layout.fragment_venda, container, false);

         cancelarBtnVenda = (Button) view.findViewById(R.id.CancelarVenda);
         fotocarro = (ImageView) view.findViewById(R.id.imagemCarro);
         Button carregarFoto = (Button) view.findViewById(R.id.btnImagem);


         cancelarBtnVenda.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                getActivity().onBackPressed();
             }
         });

         carregarFoto.setOnClickListener(abrirGaleria);
     }
        return view;
    }

    View.OnClickListener abrirGaleria = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent  intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getActivity().startActivityForResult(intent,GALERIA_IMAGENS);
        }
    };
}
