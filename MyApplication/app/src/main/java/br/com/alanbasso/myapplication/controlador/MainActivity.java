package br.com.alanbasso.myapplication.controlador;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.alanbasso.myapplication.R;
import br.com.alanbasso.myapplication.data.CulturaDAO;
import br.com.alanbasso.myapplication.data.DatePickerFragment;
import br.com.alanbasso.myapplication.data.RegistroDAO;
import br.com.alanbasso.myapplication.entity.Cultura;
import br.com.alanbasso.myapplication.entity.Registro;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alertDialog;

    private EditText edtData;
    private DatePickerFragment datePickerFragment;
    private CulturaDAO culturaDAO;
    private ArrayAdapter<Cultura> arrayAdapterCultura;

    private ArrayList<Cultura> listarItem;
    private ArrayList<Registro> listarItens;
    private SQLiteDatabase banco;
    private RegistroDAO registroDAO;
    public Registro itemSendoAlterado = null;
    Spinner spinner;

    private Cultura cultura;


    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            edtData.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtData = (EditText) findViewById(R.id.edtData);

        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Calendar calendar = Calendar.getInstance();
                bundle.putInt("dia", calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
                bundle.putInt("mes", calendar.get(Calendar.MONTH));
                bundle.putInt("ano", calendar.get(Calendar.YEAR));

                datePickerFragment = new DatePickerFragment();
                datePickerFragment.setArguments(bundle);
                datePickerFragment.setListener(dateListener);
                datePickerFragment.show(getFragmentManager(), "Data");
            }
        });

        //spinner
        culturaDAO = new CulturaDAO(this);
        listarItem = culturaDAO.listarCulturas();
        arrayAdapterCultura = new ArrayAdapter<Cultura>(this, android.R.layout.simple_list_item_1, listarItem);
        spinner = findViewById(R.id.spinner2);
        spinner.setAdapter(arrayAdapterCultura);

        registroDAO = new RegistroDAO(this);

        //Checagem de miudos
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Este produto possui classificação de miudos ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // setContentView(R.layout.activity_main);

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                EditText edtMiudo = findViewById(R.id.edtMiudo);
                edtMiudo.setVisibility(View.VISIBLE);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //setContentView(R.layout.activity_main);

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

            }
        });
        alertDialog = builder.create();
        alertDialog.show();


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

    public void salvar(View view) {


        registroDAO = new RegistroDAO(this);
        EditText datas = (EditText) findViewById(R.id.edtData);
        EditText qtde = (EditText) findViewById(R.id.edtQtde);
        EditText qtde_miudo = (EditText) findViewById(R.id.edtMiudo);
        EditText edt_empresa = (EditText) findViewById(R.id.edtEps);
        EditText edt_dono = (EditText) findViewById(R.id.edtDono);
        spinner = (Spinner) findViewById(R.id.spinner2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                cultura = (Cultura) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Selecione uma cultura !",Toast.LENGTH_SHORT).show();

            }
        });

        Cultura culturas = (Cultura) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
        int cultura = culturas.getId();
        String data = datas.getText().toString();
        int quantidade = Integer.parseInt(qtde.getText().toString());
        int qtde_M = Integer.parseInt(qtde_miudo.getText().toString());
        String empresa = edt_empresa.getText().toString();
        String dono = edt_dono.getText().toString();

        Registro registro = new Registro(data, quantidade, qtde_M, cultura, dono, empresa);

        try {

            if (itemSendoAlterado == null) {
                listarItens = new ArrayList<Registro>();
                listarItens.add(registro);
                registroDAO.incluirRegistro(registro);
                Toast.makeText(this, " salvo com sucesso", Toast.LENGTH_SHORT).show();
                ocultarTeclado();
                Intent intent = new Intent(MainActivity.this, DadosActivity.class);
                startActivity(intent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void ocultarTeclado() {
        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view1.getWindowToken(), 0);

            EditText datas = (EditText) findViewById(R.id.edtData);
            EditText qtde = (EditText) findViewById(R.id.edtQtde);
            EditText qtde_miudo = (EditText) findViewById(R.id.edtMiudo);
            EditText edt_empresa = (EditText) findViewById(R.id.edtEps);
            EditText edt_dono = (EditText) findViewById(R.id.edtDono);

            datas.setText("");
            qtde.setText("");
            qtde_miudo.setText("");
            edt_dono.setText("");
            edt_empresa.setText("");
        }

    }
}
