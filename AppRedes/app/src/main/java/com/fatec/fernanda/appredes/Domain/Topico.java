package com.fatec.fernanda.appredes.Domain;

import java.util.List;

/**
 * Created by Fernanda on 15/09/2016.
 */

public class Topico {

    int id;
    String tituloTopico;
    List<Trecho> trechos;


    public Topico() {
        //
    }

    public Topico(int id, String tituloTopico) {
        this.id = id;
        this.tituloTopico = tituloTopico;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloTopico() {
        return tituloTopico;
    }

    public void setTituloTopico(String tituloTopico) {
        this.tituloTopico = tituloTopico;
    }

    public List<Trecho> getTrechos() {
        return trechos;
    }

    public void setTrechos(List<Trecho> trechos) {
        this.trechos = trechos;
    }
}
