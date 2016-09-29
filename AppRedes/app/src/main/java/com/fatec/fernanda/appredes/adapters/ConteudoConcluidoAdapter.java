package com.fatec.fernanda.appredes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.models.ConteudoConcluido;

import java.util.List;

/**
 * Created by Fernanda on 24/08/2016.
 */

public class ConteudoConcluidoAdapter extends ArrayAdapter<ConteudoConcluido> {

    private int resource;

    public ConteudoConcluidoAdapter(Context context, int resource, List<ConteudoConcluido> objects){
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(this.resource, parent, false);
        }

        ConteudoConcluido conteudo = getItem(position);

        TextView titulo = (TextView) row.findViewById(R.id.tituloConteudo);
        titulo.setText(conteudo.getNomeConteudo());

        TextView nota = (TextView) row.findViewById(R.id.notaTesteConteudo);
        nota.setText(String.valueOf(conteudo.getNotaTeste()));

        return row;
    }
}
