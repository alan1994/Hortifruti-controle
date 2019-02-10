package br.com.alanbasso.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


import br.com.alanbasso.myapplication.entity.Cultura;
import br.com.alanbasso.myapplication.entity.Registro;

public class RegistroDAO {

    private OpenHelper openHelper;
    private SQLiteDatabase database;
    private static final int DB_VERSION = 2;
    private static final String DATABASE_NAME="controle.db";
    private static final  String MY_QUERY = "SELECT * FROM registro r  INNER JOIN cultura c ON r.cultura= c._id WHERE r.cultura = ? ORDER BY date(r.data); ";

    public RegistroDAO(Context context) {
        openHelper = new OpenHelper(context, DATABASE_NAME, null, DB_VERSION);
    }

    public void abrirBanco() {

        database = openHelper.getWritableDatabase();
    }


    public void fecharBanco() {
        if (database != null) {
            database.close();
        }
    }

    public void incluirRegistro(Registro registro) {
        ContentValues values = new ContentValues();
        values.put("data", registro.getData());
        values.put("empresa", registro.getEmpresa());
        values.put("quantidade", registro.getQuantidade());
        values.put("qtde_miudo", registro.getQtde_miudo());
        values.put("cultura", registro.getCultura());
        values.put("dono", registro.getDono());

        abrirBanco();
        database.insert("registro", null, values);
        fecharBanco();
    }

    public void excluirRegistro(Registro  registro){

        abrirBanco();
        database.execSQL("DELETE FROM registro WHERE _id = ?" + registro.getId() );
        fecharBanco();

    }

    public ArrayList<Registro> listarRegistro() {
        ArrayList<Registro> listarRegistros= new ArrayList<>();
        abrirBanco();
        Cursor cursor = database.query("registro", null, null, null, null, null,
                "data, _id");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String data = cursor.getString(cursor.getColumnIndex("data"));
            String empresa = cursor.getString(cursor.getColumnIndex("empresa"));
            int qtde = cursor.getInt(cursor.getColumnIndex("quantidade"));
            int qtdemiudo = cursor.getInt(cursor.getColumnIndex("qtde_miudo"));
            String dono = cursor.getString(cursor.getColumnIndex("dono"));
            int cultura = cursor.getInt(cursor.getColumnIndex("cultura"));

            Registro registro = new Registro(id, data, qtde, qtdemiudo,cultura, dono, empresa);
            listarRegistros.add(registro);
        }
        fecharBanco();
        return listarRegistros;
    }





}
