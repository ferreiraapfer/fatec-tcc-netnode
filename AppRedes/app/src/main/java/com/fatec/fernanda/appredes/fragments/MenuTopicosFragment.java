package com.fatec.fernanda.appredes.fragments;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.fatec.fernanda.appredes.R;

/**
 * Created by Fernanda on 07/11/2016.
 */

public class MenuTopicosFragment extends LinearLayout {

    CheckedTextView checkedTextView;

    public MenuTopicosFragment(Context context){
        super(context);

        View.inflate(context, R.layout.lista_topicos, this);

        //MAPEAR COMPONENTES
        checkedTextView = (CheckedTextView) findViewById(R.id.checkTxtTituloTopico);

    }

    public String getCheckedTextView() {
        return checkedTextView.getText().toString();
    }

    public void setCheckedTextView(String text) {
        checkedTextView.setText(text);
    }

    public void setChecked(){
        checkedTextView.setChecked(Boolean.TRUE);
    }
}
