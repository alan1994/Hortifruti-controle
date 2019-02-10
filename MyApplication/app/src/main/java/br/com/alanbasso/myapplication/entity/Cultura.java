package br.com.alanbasso.myapplication.entity;

import java.io.Serializable;

public class Cultura implements Comparable, Serializable {

    private int id;
    private String nome;


    public Cultura(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Cultura() {
    }

    public Cultura(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(Object o) {
        Cultura otherCulture = (Cultura) o;
        return nome.compareTo(otherCulture.getNome());
    }

    public String toString() {

        return nome ;
    }
}

