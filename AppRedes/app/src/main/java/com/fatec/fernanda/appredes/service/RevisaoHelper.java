package com.fatec.fernanda.appredes.service;


import android.os.AsyncTask;

import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Revisao;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Fernanda on 09/11/2016.
 */

public class RevisaoHelper extends AsyncTask<ArrayList<String>, Void, ArrayList<Revisao>> {

    @Override
    protected ArrayList<Revisao> doInBackground(ArrayList<String>... arrayLists) {
        try {
            return getRevisao(arrayLists[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //dialog.show();
        System.out.println("onPreExecute");
    }

    @Override
    protected void onPostExecute(ArrayList<Revisao> result) {
        super.onPostExecute(result);
        //dialog.dismiss();
        if(result!=null){
            //criar a views
        }

        System.out.println("onPostExecute");
    }

    private ArrayList<Revisao> getRevisao(ArrayList<String> idConteudos)  throws IOException{
       final ArrayList<Revisao> arrayMinhaRevisao = new ArrayList<>();
        DatabaseReference questoesRef = FirebaseDatabase.getInstance().getReference()
                .child("questoes");

        for(final String id : idConteudos){
            questoesRef.child(id).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Revisao novaRevisao = new Revisao();

                    Questao novaQuestao = new Questao();
                    novaQuestao.setDescricao(dataSnapshot.child("descricao").getValue(String.class));
                    novaQuestao.setId(Integer.parseInt(dataSnapshot.getKey().substring(7)));
                    novaQuestao.setExplicacao(dataSnapshot.child("explicacao").getValue(String.class));

                    novaRevisao.setQuestao(novaQuestao);
                    novaRevisao.setIdConteudo(id);

                    arrayMinhaRevisao.add(novaRevisao);

                    System.out.println("add questao " + novaQuestao.getId());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        return arrayMinhaRevisao;
    }
}
