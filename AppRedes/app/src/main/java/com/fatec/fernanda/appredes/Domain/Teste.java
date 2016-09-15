package com.fatec.fernanda.appredes.Domain;

import java.util.List;

/**
 * Created by Fernanda on 15/09/2016.
 */

public class Teste {

    int id;
    Double nota;
    Conteudo conteudo;
    List<Questao> questoes;

    public Teste() {
        //
    }

    public Teste(int id, Double nota) {
        this.id = id;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Conteudo getConteudo() {
        return conteudo;
    }

    public void setConteudo(Conteudo conteudo) {
        this.conteudo = conteudo;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }
}
