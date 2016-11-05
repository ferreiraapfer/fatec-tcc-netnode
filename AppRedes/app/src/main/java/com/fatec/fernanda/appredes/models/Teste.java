package com.fatec.fernanda.appredes.models;

import java.io.Serializable;

import static java.lang.Boolean.FALSE;

/**
 * Created by Fernanda on 03/11/2016.
 */

public class Teste implements Serializable{

    Questao questao;
    int idRespostaDada;
    boolean acertou;


    public Teste() {
        acertou = FALSE;
    }

    public Questao getQuestao() {
        return questao;
    }
    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public int getIdRespostaDada() {
        return idRespostaDada;
    }

    public void setIdRespostaDada(int idRespostaDada) {
        this.idRespostaDada = idRespostaDada;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }
}
