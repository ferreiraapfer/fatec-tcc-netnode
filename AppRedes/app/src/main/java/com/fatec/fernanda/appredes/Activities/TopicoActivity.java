package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.models.Topico;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.primitives.Bytes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TopicoActivity extends AppCompatActivity {

    Topico topico;
    String tituloTopico;
    int idTopico;

    TextView txtTituloTopico;
    TextView txtConteudoTopico;

    DatabaseReference topicoRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topico);

        txtTituloTopico = (TextView) findViewById(R.id.txtTituloTopico);
        txtConteudoTopico = (TextView) findViewById(R.id.txtConteudoTopico);

        topicoRef = FirebaseDatabase.getInstance().getReference().child("topicos");

        //RECEBENDO O ID/NOME DO TOPICO
        Intent originIntent = getIntent();
        idTopico = originIntent.getExtras().getInt("idTopico");
        tituloTopico = originIntent.getExtras().getString("tituloTopico");

        topico = new Topico();
        topico.setId(idTopico);
        topico.setTitulo(tituloTopico);

        //POSICIONA PARA A ÁREA DO TOPICO NO BANCO
        topicoRef = topicoRef.child("topico" + idTopico).child("texto");

        System.out.println("topico" + idTopico);

        txtTituloTopico.setText(topico.getTitulo());

        //PEGANDO TEXTO PELO ARQUIVO HTML @ FIREBASE STORAGE
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://appredes-a8895.appspot.com");
        storageRef = storageRef.child("conteudo.html");

        File arquivoTopico = null;
        try {
            //ARQUIVO TEMPORARIO QUE RECEBERÁ O HTML
            arquivoTopico = File.createTempFile("conteudo", "html");
            final File finalArquivoTopico = arquivoTopico;

            //PEGANDO CONTEUDO DA REFERENCIA E ADICIONANDO AO ARQUIVO TEMPORARIO
            storageRef.getFile(finalArquivoTopico).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    
                    StringBuilder text = new StringBuilder();

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(finalArquivoTopico));
                        String line;

                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            text.append('\n');
                        }
                        br.close();
                    } catch (IOException e) {
                        //You'll need to add proper error handling here
                    }

                    txtConteudoTopico.setText(Html.fromHtml(text.toString()));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
