package com.fatec.fernanda.appredes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.adapter.ConteudoConcluidoAdapter;
import com.fatec.fernanda.appredes.dao.CustomSQLiteOpenHelper;
import com.fatec.fernanda.appredes.domain.ConteudoConcluido;
import com.fatec.fernanda.appredes.domain.ManageFile;
import com.fatec.fernanda.appredes.domain.Usuario;

import java.io.IOException;
import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {

    CustomSQLiteOpenHelper myDB;
    Usuario user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        final TextView emailUsuario = (TextView) findViewById(R.id.email_usuario);
        final TextView nomeUsuario = (TextView) findViewById(R.id.nome_usuario);
        final ProgressBar progresso = (ProgressBar) findViewById(R.id.progresso_barra);
        final TextView progressoTexto = (TextView) findViewById(R.id.progresso);

        ManageFile fileread = new ManageFile(getBaseContext());
        emailUsuario.setText(fileread.readFromFile());

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
