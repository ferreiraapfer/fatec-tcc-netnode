package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fatec.fernanda.appredes.LoginActivity;
import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.adapter.ConteudoConcluidoAdapter;
import com.fatec.fernanda.appredes.domain.ConteudoConcluido;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class PerfilActivity extends AppCompatActivity {

    private TextView emailUsuario;
    private TextView nomeUsuario;
    private ProgressBar progresso;
    private TextView progressoTexto;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        emailUsuario = (TextView) findViewById(R.id.email_usuario);
        nomeUsuario = (TextView) findViewById(R.id.nome_usuario);
        progresso = (ProgressBar) findViewById(R.id.progresso_barra);
        progressoTexto = (TextView) findViewById(R.id.progresso);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
        }

        FirebaseUser usuario = firebaseAuth.getCurrentUser();

        emailUsuario.setText(usuario.getEmail());
        nomeUsuario.setText(usuario.getDisplayName());


        /*

        ManageJsonFile manageJsonFile = new ManageJsonFile();


        InputStream in;
        try {
            in = getApplicationContext().openFileInput("Usuario.json");
            Usuario user = manageJsonFile.readJsonStream(in);

            emailUsuario.setText(user.getEmail());
            nomeUsuario.setText(user.getNome());
            progresso.setProgress(user.getPontuacao());
            progressoTexto.setText(String.valueOf(user.getPontuacao())+"%");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/
        //TODO arrumar pontuação texto, maximo e barra

        initConteudosConcluidosListView();
    }

    private ArrayList<ConteudoConcluido> createConteudosConcluidosList() {
        ArrayList<ConteudoConcluido> list = new ArrayList<ConteudoConcluido>();

        for (int i = 1; i <= 5; i++) {
            list.add(ConteudoConcluido.novaInstancia(i));
        }

        return list;
    }

    private void initConteudosConcluidosListView() {
        ListView listView = (ListView) findViewById(R.id.advanced_list_view);

        if (listView == null) {
            return;
        }

        ArrayList<ConteudoConcluido> list = createConteudosConcluidosList();

        ConteudoConcluidoAdapter adapter = new ConteudoConcluidoAdapter(this, R.layout.lista_conteudos_concluidos_item, list);
        listView.setAdapter(adapter);
    }


}
