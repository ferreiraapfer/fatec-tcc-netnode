package com.fatec.fernanda.appredes.domain;

/**
 * Created by Fernanda on 05/09/2016.
 */

public class Conteudo {

    String id;
    String titulo;
    String subtitulo;

    public Conteudo() {
        //
    }

    public Conteudo(String id, String titulo, String subtitulo) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
