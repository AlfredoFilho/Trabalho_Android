package trabalho_a193532_c195741.ft.unicamp.carros;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import trabalho_a193532_c195741.ft.unicamp.R;
import trabalho_a193532_c195741.ft.unicamp.database.BDHelper;

public class Carros {

   /* BDHelper bdHelper;
    SQLiteDatabase sqLiteDatabase;
    Carro[] listaCarros;

    public void consultarBanco(){

        bdHelper = new BDHelper(getActivity());
        sqLiteDatabase = bdHelper.getReadableDatabase();

        String sql = "Select * from CarrosVender";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {

                String img = cursor.getString(1);
                String modelo = cursor.getString(2);
                String estilo = cursor.getString(3);
                String ano = cursor.getString(4);
                String cor = cursor.getString(5);
                String cambio = cursor.getString(6);
                String descricao = cursor.getString(7);
                String preco = cursor.getString(8);

                listaCarros = {
                        new Carro(img, modelo, estilo, ano, cor, cambio, descricao, preco);
                };

            } while (cursor.moveToNext());

        }
        cursor.close();

    }*/

    public static Carro[] carros = {
            new Carro ("Audi TT Roadster",R.drawable.audi,"Carro super esportivo, com 350 cavalos de potencia", "Azul","235.000","2010","Manual"),
            new Carro ("BMW 320i  ",R.drawable.bmw,"Carro de luxo, completo. 16v 375 cavalos ","Azul","210.000","2010","kbhj"),
            new Carro ("Chevrolet Camaro",R.drawable.camaro,"<p>MOTOR 6.2L V8 ACELERAÇÃO DE 0 A 100 em 4,25s","jhg","Tal","195.000","2010"),
            new Carro ("Ferrari ",R.drawable.ferrari,"<p>Nada a Declarar</p>","jhg","Azul","150.000","2010"),
            new Carro ("Jeep Compass",R.drawable.jeep,"<p>Nada a Declarar</p>","hgj","Azul","150.000","2010"),
            new Carro ("Range Rover ",R.drawable.rangeroververmelha,"<p>Nada a Declarar</p>","jhg","Azul","150.000","2005"),
            new Carro ("Range Rover ",R.drawable.range,"<p>Me chamo </p>","sciut","Azul","150.000","2010")
    };
}
