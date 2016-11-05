package com.fatec.fernanda.appredes.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fernanda on 05/11/2016.
 */

public class DataWrapper implements Serializable {

    private ArrayList<Teste> arrayTeste;

    public void setArrayTeste(ArrayList<Teste> data) {
        this.arrayTeste = data;
    }

    public ArrayList<Teste> getArrayTeste() {
        return this.arrayTeste;
    }

}
