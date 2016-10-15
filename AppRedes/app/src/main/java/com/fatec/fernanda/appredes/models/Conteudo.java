package com.fatec.fernanda.appredes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fernanda on 05/09/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conteudo {
    String titulo;
    String id;
    /*

    List<Topico> topicos;

     */

    public Conteudo() {
        //
    }

    public Conteudo(String titulo, String id) {
        this.titulo = titulo;
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
