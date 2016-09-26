package com.fatec.fernanda.appredes.models;

/**
 * Created by Fernanda on 15/09/2016.
 */

public class Topico {

    int idConteudo;
    int idTopico;
    String tituloTopico;

    //String tituloTopico;
    //List<Trecho> trechos;


    public Topico() {
        //
    }

    public int getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(int idTopico) {
        this.idTopico = idTopico;
    }

    public int getIdConteudo() {
        return idConteudo;
    }

    public void setIdConteudo(int idConteudo) {
        this.idConteudo = idConteudo;
    }

    public String getTituloTopico() {
        return tituloTopico;
    }

    public void setTituloTopico(String tituloTopico) {
        this.tituloTopico = tituloTopico;
    }
}
