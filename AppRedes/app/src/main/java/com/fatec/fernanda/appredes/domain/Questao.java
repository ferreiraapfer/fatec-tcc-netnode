package com.fatec.fernanda.appredes.domain;

import java.util.List;

/**
 * Created by Fernanda on 15/09/2016.
 */

public class Questao {

    int id;
    String descricao;
    Topico topico;
    List<Resposta> respostas;
    Resposta respostaCorreta;

    public Questao() {
        //
    }

    public Questao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
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
}
