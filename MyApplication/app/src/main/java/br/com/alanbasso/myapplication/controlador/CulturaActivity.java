package br.com.alanbasso.myapplication.controlador;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import br.com.alanbasso.myapplication.R;
import br.com.alanbasso.myapplication.data.CulturaDAO;
import br.com.alanbasso.myapplication.entity.Cultura;

public class CulturaActivity extends AppCompatActivity {


    private CulturaDAO culturaDAO;
    private ArrayAdapter<Cultura> arrayAdapter;
    private ArrayList<Cultura> listarItens;
    public Cultura itemSendoAlterado = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultura);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.action_culturas);

        culturaDAO = new CulturaDAO(this);
        listarItens = culturaDAO.listarCulturas();
        arrayAdapter = new ArrayAdapter<Cultura>(this, android.R.layout.simple_list_item_1, listarItens);
        final ListView listView = findViewById(R.id.lstViewCulturas);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemSendoAlterado = listarItens.get(i);
                EditText edtCultura = (EditText) findViewById(R.id.edtCulturaNome);
                edtCultura.setText(itemSendoAlterado.getNome());


                Button btn = (Button) findViewById(R.id.btnCadCultura);
                btn.setText("Alterar");
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cultura exclirCultura = listarItens.get(i);
                culturaDAO.excluirCultura(exclirCultura);
                listarItens.remove(i);
                arrayAdapter.notifyDataSetChanged();

            }

        });
    }


    public void salvar(View view) {

        culturaDAO = new CulturaDAO(this);
        EditText edtCultura = (EditText) findViewById(R.id.edtCulturaNome);
        String nome = edtCultura.getText().toString();
        Cultura cultura = new Cultura(nome);

        if (itemSendoAlterado == null) {


            culturaDAO.incluirCultura(cultura);
            listarItens.add(cultura);
        } else {
            itemSendoAlterado.setNome(nome);
            culturaDAO.alterarCultura(itemSendoAlterado);
            for (int i = 0; i < listarItens.size(); i++) {
                if (listarItens.get(i).getId() == itemSendoAlterado.getId()) {
                    listarItens.set(i, itemSendoAlterado);
                    break;
                }
            }
            itemSendoAlterado = null;
            Button btn = (Button) findViewById(R.id.btnCadCultura);
            btn.setText("Incluir");


        }
        Collections.sort(listarItens);
        arrayAdapter.notifyDataSetChanged();
        Toast.makeText(this, edtCultura.getText()+ " salvo com sucesso", Toast.LENGTH_SHORT).show();

        listarItens = culturaDAO.listarCulturas();
        arrayAdapter = new ArrayAdapter<Cultura>(this, android.R.layout.simple_list_item_1, listarItens);

        final ListView listView = (ListView) findViewById(R.id.lstViewCulturas);
        listView.setAdapter(arrayAdapter);
        ocultarTeclado();
        edtCultura.setText("");

    }


    public void ocultarTeclado() {
        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
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
