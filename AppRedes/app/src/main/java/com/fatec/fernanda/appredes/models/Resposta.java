package com.fatec.fernanda.appredes.models;

/**
 * Created by Fernanda on 15/09/2016.
 */

public class Resposta {

    int id;
    String descricao;
    String justificativa;

    public Resposta() {
        //
    }

    public Resposta(int id, String descricao, String justificativa) {
        this.id = id;
        this.descricao = descricao;
        this.justificativa = justificativa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}
