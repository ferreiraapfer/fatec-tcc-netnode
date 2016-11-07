package com.fatec.fernanda.appredes.models;

import java.util.Random;

/**
 * Created by Fernanda on 24/08/2016.
 */

public class TesteRealizado {
    private int idConteudo;
    private String nomeConteudo;
    private double notaTeste;

    private static String[] conteudos = new String[]{
            "Introdução", "História", "Redes sem Fio", "Camada de Aplicação", "Camada de Rede"};

    public static TesteRealizado novaInstancia(int id) {
        Random random = new Random();

        TesteRealizado conteudo = new TesteRealizado();
        conteudo.setNomeConteudo(conteudos[id - 1]);
        conteudo.setNotaTeste(1 + random.nextInt(10));

        return conteudo;
    }

    public int getIdConteudo() {
        return idConteudo;
    }

    public void setIdConteudo(int idConteudo) {
        this.idConteudo = idConteudo;
    }

    public String getNomeConteudo() {
        return nomeConteudo;
    }

    public void setNomeConteudo(String nomeConteudo) {
        this.nomeConteudo = nomeConteudo;
    }

    public double getNotaTeste() {
        return notaTeste;
    }

    public void setNotaTeste(double notaTeste) {
        this.notaTeste = notaTeste;
    }

}
