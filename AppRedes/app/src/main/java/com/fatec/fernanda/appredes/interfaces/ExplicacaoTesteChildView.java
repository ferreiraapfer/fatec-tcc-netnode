package com.fatec.fernanda.appredes.interfaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

    public void setImgPergunta(int idPergunta, int idConteudo) {

        StorageReference questaoRef = FirebaseStorage.getInstance().getReference().child("questoes")
                .child("conteudo" + idConteudo).child("questao" + idPergunta + ".png");
        questaoRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgPergunta.setImageBitmap(bmp);
                imgPergunta.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle any erros
            }
        });

    }

    public void setTxtResposta(String txtResposta) {
        this.txtResposta.setText(txtResposta);
    }

    public void setTxtExplicacao(String txtExplicacao) {
        this.txtExplicacao.setText(txtExplicacao);
    }

    public void setTxtColorErrou() {
        txtResposta.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
    }
}
