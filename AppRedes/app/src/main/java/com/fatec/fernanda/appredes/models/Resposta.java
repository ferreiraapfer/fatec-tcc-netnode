package com.fatec.fernanda.appredes.models;

import java.io.Serializable;

/**
 * Created by Fernanda on 15/09/2016.
 */

public class Resposta implements Serializable {

    int id;
    String descricao;
    String explicacao;

    public Resposta() {
        //
    }

    public Resposta(int id, String descricao, String explicacao) {
        this.id = id;
        this.descricao = descricao;
        this.explicacao = explicacao;
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

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }
}
