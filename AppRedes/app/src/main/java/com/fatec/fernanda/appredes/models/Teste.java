package com.fatec.fernanda.appredes.models;

import java.io.Serializable;

/**
 * Created by Fernanda on 03/11/2016.
 */

public class Teste implements Serializable{

    int idQuestao;
    boolean acertou;
    int idRespostaDada;

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public int getIdRespostaDada() {
        return idRespostaDada;
    }

    public void setIdRespostaDada(int idRespostaDada) {
        this.idRespostaDada = idRespostaDada;
    }
}
