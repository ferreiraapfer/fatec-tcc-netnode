package com.fatec.fernanda.appredes.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Fernanda on 12/11/2016.
 */

public class RevisaoFragment extends RelativeLayout {

    private TextView txtPergunta;
    private ImageView imgIlustracao;
    private RadioButton rbtnResposta1;
    private RadioButton rbtnResposta2;
    private RadioButton rbtnResposta3;

    public RevisaoFragment(Context context) {
        super(context);

        View.inflate(context, R.layout.fragment_questao_revisao, this);

        //MAPEAR COMPONENTES
        txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        imgIlustracao = (ImageView) findViewById(R.id.imgIlustracao);
        rbtnResposta1 = (RadioButton) findViewById(R.id.rbtnResposta1);
        rbtnResposta2 = (RadioButton) findViewById(R.id.rbtnResposta2);
        rbtnResposta3 = (RadioButton) findViewById(R.id.rbtnResposta3);

    }

    public TextView getTxtPergunta() {
        return txtPergunta;
    }

    public void setTxtPergunta(String txtPergunta) {
        this.txtPergunta.setText(txtPergunta);
    }

    public ImageView getImgIlustracao() {
        return imgIlustracao;
    }

    public void setImgIlustracao(int idPergunta, int idConteudo) {
        StorageReference questaoRef = FirebaseStorage.getInstance().getReference().child("questoes")
                .child("conteudo" + idConteudo).child("questao" + idPergunta + ".png");
        questaoRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgIlustracao.setImageBitmap(bmp);
                imgIlustracao.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle any erros
            }
        });
    }

    public RadioButton getRbtnResposta1() {
        return rbtnResposta1;
    }

    public void setRbtnResposta1(String rbtnResposta1) {
        this.rbtnResposta1.setText(rbtnResposta1);
    }

    public RadioButton getRbtnResposta2() {
        return rbtnResposta2;
    }

    public void setRbtnResposta2(String rbtnResposta2) {
        this.rbtnResposta2.setText(rbtnResposta2);
    }

    public RadioButton getRbtnResposta3() {
        return rbtnResposta3;
    }

    public void setRbtnResposta3(String rbtnResposta3) {
        this.rbtnResposta3.setText(rbtnResposta3);
        this.rbtnResposta3.setVisibility(View.VISIBLE);
    }
}
