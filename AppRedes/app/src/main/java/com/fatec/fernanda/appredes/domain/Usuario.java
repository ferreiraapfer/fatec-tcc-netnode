package com.fatec.fernanda.appredes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Fernanda on 27/08/2016.
 */

public class Usuario implements Parcelable {

    String email;
    String nome;
    int pontuacao;
    List<Teste> testesRealizados;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(nome);
        parcel.writeInt(pontuacao);
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    private Usuario(Parcel in) {
        email = in.readString();
        nome = in.readString();
        pontuacao = in.readInt();
    }

    public List<Teste> getTestesRealizados() {
        return testesRealizados;
    }

    public void setTestesRealizados(List<Teste> testesRealizados) {
        this.testesRealizados = testesRealizados;
    }

    public static Creator<Usuario> getCREATOR() {
        return CREATOR;
    }
}

 