package com.fatec.fernanda.appredes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Revisao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class RevisaoActivity extends AppCompatActivity {

    ArrayList<String> idConteudos;

    DatabaseReference questoesRef;

    ArrayList<Revisao> arrayMinhaRevisao;
    ArrayList<Revisao> arrayIdQuestoes;

    int numQuestoesConteudos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao);

        Intent originIntent = getIntent();
        idConteudos = originIntent.getExtras().getStringArrayList("idConteudos");
        numQuestoesConteudos = originIntent.getExtras().getInt("numQuestoesConteudos");

        arrayIdQuestoes = new ArrayList<>();

        questoesRef = FirebaseDatabase.getInstance().getReference().child("questoes");

        for (final String idConteudo : idConteudos) {
            questoesRef.child(idConteudo).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Revisao novaRevisao = new Revisao();
                    Questao novaQuestao = new Questao();

                    novaQuestao.setId(Integer.parseInt(dataSnapshot.getKey().substring(8)));

                    novaRevisao.setQuestao(novaQuestao);
                    novaRevisao.setIdConteudo(idConteudo);

                    arrayIdQuestoes.add(novaRevisao);

                    System.out.println(arrayIdQuestoes.size() + "/" + numQuestoesConteudos);

                    if (arrayIdQuestoes.size() == numQuestoesConteudos) {
                        arrayMinhaRevisao = getDezQuestoes(arrayIdQuestoes);

                        for (Revisao r : arrayMinhaRevisao) {
                            System.out.println(r.getQuestao().getId());
                        }
                    }

                    questoesRef.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


    }

    private ArrayList<Revisao> getDezQuestoes(ArrayList<Revisao> arrayIdQuestoes) {

        ArrayList<Revisao> questoes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            questoes.add((arrayIdQuestoes.get(new Random().nextInt(arrayIdQuestoes.size()))));
        }

        return questoes;
    }
}
