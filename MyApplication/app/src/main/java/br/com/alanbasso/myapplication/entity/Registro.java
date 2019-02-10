package br.com.alanbasso.myapplication.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alan on 11/05/2018.
 */

public class Registro implements Comparable, Serializable {

    private int id;

    private String data;

    private int quantidade;

    private int qtde_miudo;
    private Cultura culturas;

    private int cultura;

    private String nome;

    private String dono;

    private String empresa;


    public Registro() {

    }

    public Registro(int id, String data, int quantidade, int qtde_miudo, int cultura, String dono, String empresa) {
        this.id = id;
        this.data = data;
        this.quantidade = quantidade;
        this.qtde_miudo = qtde_miudo;

        this.cultura = cultura;
        this.dono = dono;
        this.empresa = empresa;


    }

    public Registro(String data, int quantidade, int qtde_miudo, int cultura, String dono, String empresa) {

        this.data = data;
        this.quantidade = quantidade;
        this.qtde_miudo = qtde_miudo;
        this.cultura = cultura;
        this.dono = dono;
        this.empresa = empresa;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {

        Locale locale = new Locale("pt", "BR");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", locale).parse(data);

            return new SimpleDateFormat("dd/MM/yyyy", locale).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQtde_miudo() {
        return qtde_miudo;
    }

    public void setQtde_miudo(int qtde_miudo) {
        this.qtde_miudo = qtde_miudo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCultura() {
        return cultura;
    }

    public void setCultura(int cultura) {
        this.cultura = cultura;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public int compareTo(Object o) {
        Registro registro = (Registro) o;
        return registro.compareTo(registro.cultura);
    }

    public String toString() {

        return "Dia: " + data +"\n" + "Quantidade: " + quantidade + "\n"+ "Quantidade Miudo: " + qtde_miudo +
                "\n" + "Dono: " + dono + "\n"+ "Empresa: " +  empresa;
    }
}
