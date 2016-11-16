package com.fatec.fernanda.appredes.fragments;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.fatec.fernanda.appredes.R;

/**
 * Created by Fernanda on 07/11/2016.
 */

public class MenuTestesFragment extends LinearLayout {

    CheckedTextView checkedTextView;

    public MenuTestesFragment(Context context){
        super(context);

        View.inflate(context, R.layout.lista_conteudos, this);

        //MAPEAR COMPONENTES
        checkedTextView = (CheckedTextView) findViewById(R.id.checkTxtTituloConteudo);

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

    public Boolean isChecked() {
        if(checkedTextView.isChecked()){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
}
