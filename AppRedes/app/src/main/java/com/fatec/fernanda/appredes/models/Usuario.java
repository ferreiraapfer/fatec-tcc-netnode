package com.fatec.fernanda.appredes.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Fernanda on 27/08/2016.
 */

public class Usuario {

    String email;
    String nome;
    int pontuacao;
    List<TesteRealizado> testesRealizados;

    public Usuario(String email, String nome) {
        this.email = email;
        this.nome = nome;
        pontuacao = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }


    public List<TesteRealizado> getTestesRealizados() {
        return testesRealizados;
    }

    public void setTestesRealizados(List<TesteRealizado> testesRealizados) {
        this.testesRealizados = testesRealizados;
    }
}

 