package br.com.alanbasso.myapplication.data;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    private int dia, mes, ano;

    public DatePickerFragment(){

    }

    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        ano = args.getInt("ano");
        mes = args.getInt("mes");
        dia = args.getInt("dia");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), listener, ano, mes, dia);
    }
}
