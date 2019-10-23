package trabalho_a193532_c195741.ft.unicamp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHelper  extends SQLiteOpenHelper {


    private static final String DB_NAME = "LahayBD";
    private static final int DB_VERSION = 1;

    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CarrosVender (_id INTEGER PRIMARY KEY AUTOINCREMENT, img Text, modelo Text, estilo Text, ano INTEGER, cor Text, cambio Text, aro INTEGER,  descricao Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}
