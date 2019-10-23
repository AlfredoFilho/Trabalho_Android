package trabalho_a193532_c195741.ft.unicamp.carros.venda;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;

import trabalho_a193532_c195741.ft.unicamp.R;
import trabalho_a193532_c195741.ft.unicamp.database.BDHelper;

import static android.app.Activity.RESULT_OK;

public class VendaFragment extends Fragment {

    private EditText modelo;
    private RadioGroup radiotipo;
    private RadioButton esportivo;
    private RadioButton conversivel;
    private Spinner ano;
    private Spinner cor;
    private RadioButton cambio;
    private EditText descricao;
    private View view;
    private Button carregarFoto;
    private ImageView fotocarro;

    BDHelper bdHelper;
    SQLiteDatabase sqLiteDatabase;

    public VendaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bdHelper = new BDHelper(getActivity());
        sqLiteDatabase = bdHelper.getReadableDatabase();





        if (view == null) {
            view = inflater.inflate(R.layout.fragment_venda, container, false);
            carregarFoto = view.findViewById(R.id.btnFoto);
            fotocarro = view.findViewById(R.id.fotoCarro);

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

        modelo = view.findViewById(R.id.modelo);
        radiotipo = view.findViewById(R.id.radioTipo);
        esportivo = view.findViewById(R.id.esportivo);
        conversivel = view.findViewById(R.id.conversivel);
        ano = view.findViewById(R.id.spinnerAno);
        cor = view.findViewById(R.id.spinnerCor);
//      cambio = view.findViewById(R.id.radioCambio);
        descricao = view.findViewById(R.id.descricao);

        view.findViewById(R.id.CancelarVenda).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.cadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarVenda();
            }
        });
        return view;
    }

    public void cadastrarVenda() {

        RadioButton radioSelectButton;

        String TABLE_NAME = "CarrosVender";

        int selectedId = radiotipo.getCheckedRadioButtonId();
        radioSelectButton = view.findViewById(selectedId);


        ContentValues contentValues = new ContentValues();

        contentValues.put("img", "imagemTeste");
        contentValues.put("modelo", modelo.getText().toString());
        contentValues.put("estilo", radioSelectButton.getText().toString());
        contentValues.put("ano", ano.getSelectedItem().toString());
        contentValues.put("cor", cor.getSelectedItem().toString());
        contentValues.put("cambio", radioSelectButton.getText().toString());
        contentValues.put("descricao", descricao.getText().toString());

        sqLiteDatabase.execSQL("delete from " + TABLE_NAME);

        sqLiteDatabase.insert("CarrosVender", null, contentValues);

        testeCadastro();

    }

    public void testeCadastro() {

        String sql = "Select * from CarrosVender";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                System.out.println(cursor.getString(0));
                System.out.println(cursor.getString(1));
                System.out.println(cursor.getString(2));
                System.out.println(cursor.getString(3));
                System.out.println(cursor.getString(4));
                System.out.println(cursor.getString(5));
                System.out.println(cursor.getString(6));
                System.out.println(cursor.getString(7));
                System.out.println(cursor.getString(8));
            } while (cursor.moveToNext());

        }
        cursor.close();
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
    }
}