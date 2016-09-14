package com.fatec.fernanda.appredes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fernanda on 05/09/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conteudo {
    String titulo;

    public Conteudo() {
        //
    }

    public Conteudo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
