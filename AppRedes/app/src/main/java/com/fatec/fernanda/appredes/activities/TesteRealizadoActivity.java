package com.fatec.fernanda.appredes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.adapters.TesteRealizadoAdapter;
import com.fatec.fernanda.appredes.models.TesteRealizado;

import java.util.ArrayList;

/**
 * Created by Fernanda on 24/08/2016.
 */

public class TesteRealizadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_testes_realizados_item);

        initTesteRealizadoListView();
    }

    private ArrayList<TesteRealizado> createTesteRealizadoList() {
        ArrayList<TesteRealizado> list = new ArrayList<TesteRealizado>();

        for (int i = 1; i <= 5; i++) {
            list.add(TesteRealizado.novaInstancia(i));
        }

        return list;
    }

    private void initTesteRealizadoListView() {
        ListView listView = (ListView) findViewById(R.id.advanced_list_view);

        if (listView == null) {
            return;
        }

        ArrayList<TesteRealizado> list = createTesteRealizadoList();

        TesteRealizadoAdapter adapter = new TesteRealizadoAdapter(this, R.layout.lista_testes_realizados_item, list);
        listView.setAdapter(adapter);
    }

}
