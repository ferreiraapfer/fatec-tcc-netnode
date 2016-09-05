package com.fatec.fernanda.appredes.domain;

/**
 * Created by Fernanda on 05/09/2016.
 */

public class Conteudo {

    int id;
    String titulo;
    String subtitulo;

    public Conteudo() {
        //
    }

    public Conteudo(int id, String titulo, String subtitulo) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
}
