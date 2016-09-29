package com.fatec.fernanda.appredes.domain;

import android.os.Bundle;

/**
 * Created by Fernanda on 15/09/2016.
 */

public class Trecho {

    int id;
    String texto;
    Bundle imagem;

    public Trecho() {
        //
    }

    public Trecho(int id, String texto, Bundle imagem) {
        this.id = id;
        this.texto = texto;
        this.imagem = imagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Bundle getImagem() {
        return imagem;
    }

    public void setImagem(Bundle imagem) {
        this.imagem = imagem;
    }
}
