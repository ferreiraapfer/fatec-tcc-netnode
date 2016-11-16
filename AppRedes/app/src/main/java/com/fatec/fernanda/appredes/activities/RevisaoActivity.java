package com.fatec.fernanda.appredes.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.fragments.OnGetDataListener;
import com.fatec.fernanda.appredes.fragments.RevisaoFragment;
import com.fatec.fernanda.appredes.models.DataWrapper;
import com.fatec.fernanda.appredes.models.Questao;
import com.fatec.fernanda.appredes.models.Resposta;
import com.fatec.fernanda.appredes.models.Revisao;
import com.fatec.fernanda.appredes.models.Teste;
import com.fatec.fernanda.appredes.service.RevisaoService;
import com.fatec.fernanda.appredes.tasks.impl.AsyncTaskListener;
import com.fatec.fernanda.appredes.tasks.impl.AsyncTaskRevisao;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RevisaoActivity extends AppCompatActivity {

    ArrayList<String> idConteudos;
    ArrayList<Revisao> arrayMinhaRevisao;
    int numQuestoesConteudos;

    ProgressBar progBarTotalQuestoes;

    TextView txtPergunta;
    ImageView imgIlustracao;

    RadioGroup radioGroup;

    RadioButton rbResposta1;
    RadioButton rbResposta2;
    RadioButton rbResposta3;

    Button btnProxQuestao;
    Button btnConcluirRevisao;

    DatabaseReference questaoRef;
    DatabaseReference questoesConteudoRef;
    DatabaseReference respostasRef;

    Questao novaQuestao;

    ArrayList<Resposta> respostas;
    Resposta respostaCerta;

    DataWrapper dataWrapper;

    ArrayList<String> idQuestoes;
    ArrayList<String> arrayStringRespostas;

    int idConteudo;
    int numQuestoes;
    int idProxQuestao;
    int qtdRespostas;
    int contQuestao;

    ArrayList<Teste> arrayMeuTeste;

    Handler handler;


    ArrayList<Questao> questoes;
    LinearLayout linLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao);

        linLayout = (LinearLayout) findViewById(R.id.linLayoutRevisao);

        Intent originIntent = getIntent();
        idConteudos = originIntent.getExtras().getStringArrayList("idConteudos");
        numQuestoesConteudos = originIntent.getExtras().getInt("numQuestoesConteudos");
        arrayMinhaRevisao = new ArrayList<>();

        questoes = new ArrayList<>();


        for (String id : idConteudos) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                    .child("questoes").child(id);

            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Questao novaQuestao = new Questao();

                    novaQuestao.setDescricao(dataSnapshot.child("descricao").getValue(String.class));
                    novaQuestao.setExplicacao(dataSnapshot.child("explicacao").getValue(String.class));
                    novaQuestao.setId(Integer.parseInt(dataSnapshot.getKey().substring(7)));

                    RevisaoFragment frag = addNewQuestion(novaQuestao);
                    getRespostasFromQuestions(dataSnapshot.child("respostas"), novaQuestao, frag);

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

    }

    private RevisaoFragment addNewQuestion(Questao novaQuestao) {

        RevisaoFragment revisaoFragment = new RevisaoFragment(this);
        revisaoFragment.setTxtPergunta(novaQuestao.getDescricao());

        return revisaoFragment;

    }

    private void getRespostasFromQuestions(DataSnapshot dataSnapshot, Questao novaQuestao, final RevisaoFragment frag) {
        final int[] numResposta = {0};

        final ArrayList<Resposta> respostas = new ArrayList<>();

        DatabaseReference respostasRef = FirebaseDatabase.getInstance().getReference().child("respostas");

        for (final DataSnapshot data : dataSnapshot.getChildren()) {
            respostasRef.child(data.getKey()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    numResposta[0] = numResposta[0] + 1;

                    System.out.println(data.getKey());

                    Resposta novaResposta = new Resposta();
                    novaResposta.setId(Integer.parseInt(dataSnapshot.getKey().substring(8)));
                    novaResposta.setDescricao(dataSnapshot.child("descricao").getValue(String.class));
                    novaResposta.setExplicacao(dataSnapshot.child("explicacao").getValue(String.class));

                    respostas.add(novaResposta);

                    switch (numResposta[0]) {
                        case 1:
                            frag.setRbtnResposta1(novaResposta.getDescricao());
                            break;
                        case 2:
                            frag.setRbtnResposta2(novaResposta.getDescricao());
                            break;
                        case 3:
                            frag.setRbtnResposta3(novaResposta.getDescricao());

                            linLayout.addView(frag);
                            System.out.println("nova view");
                            break;
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}


