package com.fatec.fernanda.appredes.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.fatec.fernanda.appredes.R;

/**
 * Created by Fernanda on 08/11/2016.
 */

public class MenuRevisaoChildView extends LinearLayout {

    CheckedTextView checkedTextView;
    String idConteudo;
    int numQuestoes;

    public MenuRevisaoChildView(Context context){
        super(context);

        View.inflate(context, R.layout.lista_revisao, this);

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

    public void setUnchecked(){
        checkedTextView.setChecked(Boolean.FALSE);
    }

    public Boolean isChecked() {
        if(checkedTextView.isChecked()){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    public String getIdConteudo() {
        return idConteudo;
    }

    public void setIdConteudo(String idConteudo) {
        this.idConteudo = idConteudo;
    }


    public int getNumQuestoes() {
        return numQuestoes;
    }

    public void setNumQuestoes(int numQuestoes) {
        this.numQuestoes = numQuestoes;
    }
}
