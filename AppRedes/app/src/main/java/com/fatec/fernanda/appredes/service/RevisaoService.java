package com.fatec.fernanda.appredes.service;

import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Revisao;
import com.fatec.fernanda.appredes.models.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Fernanda on 16/11/2016.
 */

public class RevisaoService {

    ArrayList<Questao> questoes;


    public void setQuestoes(DatabaseReference reference) {

        questoes = new ArrayList<>();


    }

    public ArrayList<Questao> getQuestoes() {
        return questoes;
    }
}
