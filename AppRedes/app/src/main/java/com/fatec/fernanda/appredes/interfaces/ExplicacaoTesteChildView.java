package com.fatec.fernanda.appredes.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;

/**
 * Created by Fernanda on 04/11/2016.
 */

public class ExplicacaoTesteChildView extends LinearLayout {

    private TextView txtNumPergunta;
    private TextView txtPergunta;
    private ImageView imgPergunta;
    private TextView txtResposta;
    private TextView txtExplicacao;

    public ExplicacaoTesteChildView(Context context) {
        super(context);

        View.inflate(context, R.layout.lista_correcao_teste, this);

        //MAPEAR COMPONENTES
        txtNumPergunta = (TextView) findViewById(R.id.txtNumQuestao);
        txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        imgPergunta = (ImageView) findViewById(R.id.imgPergunta);
        txtResposta = (TextView) findViewById(R.id.txtResposta);
        txtExplicacao = (TextView) findViewById(R.id.txtExplicao);
    }

    public void setTxtNumPergunta(String txtNumPergunta) {
        this.txtNumPergunta.setText(txtNumPergunta);
    }

    public void setTxtPergunta(String txtPergunta) {
        this.txtPergunta.setText(txtPergunta);
    }

    public void setImgPergunta(ImageView imgPergunta) {
        this.imgPergunta = imgPergunta;
    }

    public void setTxtResposta(String txtResposta) {
        this.txtResposta.setText(txtResposta);
    }

    public void setTxtExplicacao(String txtExplicacao) {
        this.txtExplicacao.setText(txtExplicacao);
    }

    public void acertou (){
        txtResposta.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
    }
}
