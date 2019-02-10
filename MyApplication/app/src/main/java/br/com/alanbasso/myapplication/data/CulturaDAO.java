package br.com.alanbasso.myapplication.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.alanbasso.myapplication.entity.Cultura;

public class CulturaDAO {


    private OpenHelper openHelper;
    private SQLiteDatabase banco;
    private static final int DB_VERSION = 2;
    private static final String DATABASE_NAME="controle.db";

    public CulturaDAO(Context context) {
       openHelper = new OpenHelper(context, DATABASE_NAME, null, DB_VERSION);
    }

    private void abrirBanco (){
        banco = openHelper.getWritableDatabase();
    }

    private void fecharBanco (){
        if (banco != null){
            banco.close();
        }
    }

    public void incluirCultura(Cultura cultura) {
        ContentValues values = new ContentValues();
        values.put("nome", cultura.getNome());
        abrirBanco();
        banco.insert("cultura", null, values);
        fecharBanco();
    }

    public void excluirCultura(Cultura cultura) {
        abrirBanco();
        banco.delete("cultura", "_id = " + cultura.getId(), null);
        fecharBanco();
    }

    public void alterarCultura(Cultura cultura) {
        ContentValues values = new ContentValues();
        values.put("nome", cultura.getNome());
        abrirBanco();
        banco.update("cultura", values, "_id = " + cultura.getId(), null);
        fecharBanco();
    }

    public ArrayList<Cultura> listarCulturas() {
        ArrayList<Cultura> listarCulturas = new ArrayList<>();
        abrirBanco();
        Cursor cursor = banco.query("cultura", null, null, null, null, null, "nome, _id");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));

            Cultura cultura = new Cultura(id, nome);
            listarCulturas.add(cultura);
        }
        fecharBanco();
        return listarCulturas;
    }
}
