package br.com.alanbasso.myapplication.controlador;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.alanbasso.myapplication.R;

import br.com.alanbasso.myapplication.data.CulturaDAO;
import br.com.alanbasso.myapplication.data.OpenHelper;
import br.com.alanbasso.myapplication.data.RegistroDAO;

import br.com.alanbasso.myapplication.entity.Cultura;
import br.com.alanbasso.myapplication.entity.Registro;


public class DadosActivity extends AppCompatActivity {


    private RegistroDAO registroDAO;
    private ArrayAdapter<Registro> arrayAdapterRegistro;
    private ArrayList<Registro> listarRegistros;
    private CulturaDAO culturaDAO;
    private ArrayList<Cultura> listarCultura;
    private ArrayAdapter<Cultura> arrayAdapterCultura;
    private Spinner spinner;
    Cultura cultura;
    Registro registro;
    SQLiteDatabase sqLiteDatabase;
    OpenHelper openHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);
        openHelper = new OpenHelper(this, "controle.db", null, 2);
        sqLiteDatabase = openHelper.getReadableDatabase();
        registroDAO = new RegistroDAO(this);


        try {
            culturaDAO = new CulturaDAO(this);
            listarCultura = culturaDAO.listarCulturas();
            arrayAdapterCultura = new ArrayAdapter<Cultura>(DadosActivity.this, android.R.layout.simple_list_item_1, listarCultura);
            spinner = findViewById(R.id.spnCultura);
            spinner.setAdapter(arrayAdapterCultura);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    cultura = (Cultura) adapterView.getItemAtPosition(i);
                    Cursor cursor = sqLiteDatabase.query("registro", null, "cultura = "
                            + cultura.getId(), null, null, null, "data");

                    listarRegistros = new ArrayList<Registro>();
                    if (cursor.moveToNext()) {
                        do {
                            registro = new Registro();

                            registro.setData(cursor.getString(cursor.getColumnIndex("data")));
                            registro.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
                            registro.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
                            registro.setQtde_miudo(cursor.getInt(cursor.getColumnIndex("qtde_miudo")));
                            registro.setDono(cursor.getString(cursor.getColumnIndex("dono")));

                            listarRegistros.add(registro);
                        }

                        while (cursor.moveToNext());
                        arrayAdapterRegistro = new ArrayAdapter<Registro>(DadosActivity.this,
                                android.R.layout.simple_list_item_1, listarRegistros);
                    }
                    final ListView listView = (ListView) findViewById(R.id.listViewRegistro);
                    listView.setAdapter(arrayAdapterRegistro);
                    arrayAdapterRegistro.notifyDataSetChanged();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(DadosActivity.this, "Selecione uma opção", Toast.LENGTH_SHORT);

                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView listView = findViewById(R.id.listViewRegistro);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Registro registro1 = (Registro) adapterView.getItemAtPosition(i);
                listarRegistros = registroDAO.listarRegistro();
                
                registroDAO.excluirRegistro(registro1);
                listarRegistros.remove(i);
                arrayAdapterRegistro.notifyDataSetChanged();



                }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();

        switch (item.getItemId()) {
            case R.id.action_newExit:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_culturas:
                intent = new Intent(this, CulturaActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_dados:
                intent = new Intent(this, DadosActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
