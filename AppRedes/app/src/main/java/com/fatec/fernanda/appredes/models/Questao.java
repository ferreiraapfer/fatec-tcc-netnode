package com.fatec.fernanda.appredes.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fernanda on 15/09/2016.
 */

public class Questao implements Serializable {

    int id;
    String descricao;
    String explicacao;
    Conteudo conteudo;
    List<Resposta> respostas;
    Resposta respostaCorreta;

    public Questao() {
        //
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

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public Resposta getRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(Resposta respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }

    public Conteudo getConteudo() {
        return conteudo;
    }

    public void setConteudo(Conteudo conteudo) {
        this.conteudo = conteudo;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }
}
