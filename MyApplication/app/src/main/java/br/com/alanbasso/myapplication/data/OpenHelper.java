package br.com.alanbasso.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {


    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE cultura (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT)";
        sqLiteDatabase.execSQL(sql);


        sql = "CREATE TABLE registro (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data TEXT," +
                "empresa TEXT," +
                "quantidade INT," +
                "qtde_miudo INT," +
                "cultura INTEGER," +
                "dono TEXT)";

        sqLiteDatabase.execSQL(sql);


        sql = "INSERT INTO cultura (nome) VALUES ('Tomate')";

        sqLiteDatabase.execSQL(sql);

        String sql1 = "INSERT INTO registro(data, empresa, quantidade, qtde_miudo,cultura, dono ) VALUES('20/09/2018', 'Polzin', 200, 12, 1, 'JOSE')";
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE registro");
        sqLiteDatabase.execSQL("DROP TABLE cultura");
        onCreate(sqLiteDatabase);
    }

}

