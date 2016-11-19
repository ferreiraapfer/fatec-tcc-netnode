package com.fatec.fernanda.appredes.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.models.Topico;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TopicoActivity extends AppCompatActivity {

    Topico topico;
    String tituloTopico;
    int idTopico;
    int idConteudo;

    TextView txtTituloTopico;

    TextView txtTopico1;
    TextView txtTopico2;
    TextView txtTopico3;

    ImageView img1;
    ImageView img2;
    ImageView img3;

    Button btnConcluirTopico;

    DatabaseReference topicoRef;
    DatabaseReference usuarioRef;

    String uIdUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topico);

        txtTituloTopico = (TextView) findViewById(R.id.txtTituloTopico);

        txtTopico1 = (TextView) findViewById(R.id.txtTopico1);
        txtTopico2 = (TextView) findViewById(R.id.txtTopico2);
        txtTopico3 = (TextView) findViewById(R.id.txtTopico3);

        img1 = (ImageView) findViewById(R.id.imgTopico1);
        img2 = (ImageView) findViewById(R.id.imgTopico2);
        img3 = (ImageView) findViewById(R.id.imgTopico3);


        btnConcluirTopico = (Button) findViewById(R.id.btnConcluirTopico);

        topicoRef = FirebaseDatabase.getInstance().getReference().child("topicos");

        //RECEBENDO O ID/NOME DO TOPICO
        Intent originIntent = getIntent();
        idConteudo = originIntent.getExtras().getInt("idConteudo");

        idTopico = originIntent.getExtras().getInt("idTopico");
        tituloTopico = originIntent.getExtras().getString("tituloTopico");

        topico = new Topico();
        topico.setId(idTopico);
        topico.setTitulo(tituloTopico);

        txtTituloTopico.setText(topico.getTitulo());


        //RECEBENDO ID DO CONTEUDO

        uIdUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(uIdUsuario);

        usuarioRef.child("topicosConcluidos").child("conteudo" + idConteudo).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Se terminou o topico em questão
                if (dataSnapshot.getKey().equals("topico" + idTopico)) {
                    if (dataSnapshot.getValue(Boolean.class)) {
                        invalidaBotao();

                        usuarioRef.child("topicosConcluidos").child("conteudo" + idConteudo).removeEventListener(this);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //POSICIONA PARA A ÁREA DO TOPICO NO BANCO
        topicoRef = topicoRef.child("topico" + idTopico).child("texto");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://appredes-a8895.appspot.com");

        storageRef = storageRef.child("conteudos").child("conteudo" + idConteudo).child("topico" + idTopico);

        StorageReference html1Ref = storageRef.child("topico" + idTopico + "-1.html");
        StorageReference html2Ref = storageRef.child("topico" + idTopico + "-2.html");
        StorageReference html3Ref = storageRef.child("topico" + idTopico + "-3.html");


        //PEGANDO PRIMEIRA PARTE DO TEXTO
        try {
            final File flParte1 = File.createTempFile("parte1", "html");
            final StringBuilder sbTexto1 = new StringBuilder();

            html1Ref.getFile(flParte1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(flParte1));

                        String line;

                        while ((line = br.readLine()) != null) {
                            sbTexto1.append(line);
                            sbTexto1.append('\n');
                        }
                        br.close();

                        txtTopico1.setText(Html.fromHtml(sbTexto1.toString()));

                    } catch (IOException e) {
                        //You'll need to add proper error handling here
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }


        //PEGANDO SEGUNDA PARTE DO TEXTO
        try {
            final File flParte2 = File.createTempFile("parte2", "html");
            final StringBuilder sbTexto2 = new StringBuilder();

            html2Ref.getFile(flParte2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(flParte2));

                        String line;

                        while ((line = br.readLine()) != null) {
                            sbTexto2.append(line);
                            sbTexto2.append('\n');
                        }
                        br.close();

                        txtTopico2.setText(Html.fromHtml(sbTexto2.toString()));

                    } catch (IOException e) {
                        //You'll need to add proper error handling here
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

        //PEGANDO TERCEIRA PARTE DO TEXTO
        try {
            final File flParte3 = File.createTempFile("parte3", "html");
            final StringBuilder sbTexto3 = new StringBuilder();

            html3Ref.getFile(flParte3).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(flParte3));

                        String line;

                        while ((line = br.readLine()) != null) {
                            sbTexto3.append(line);
                            sbTexto3.append('\n');
                        }
                        br.close();

                        txtTopico3.setText(Html.fromHtml(sbTexto3.toString()));

                    } catch (IOException e) {
                        //You'll need to add proper error handling here
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            showImagens(storageRef, 1, img1);
            showImagens(storageRef, 2, img2);
            showImagens(storageRef, 3, img3);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //CONCLUIR TOPICO
        btnConcluirTopico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Long[] stringProgUsuario = new Long[1];

                usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(uIdUsuario);

                usuarioRef.child("topicosConcluidos").child("conteudo" + idConteudo).child("topico" + idTopico).setValue(true);
                usuarioRef.child("topicosConcluidos").child("conteudo" + idConteudo).child("topico" + (idTopico + 1)).setValue(false);


                usuarioRef.child("progresso").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        stringProgUsuario[0] = dataSnapshot.getValue(Long.class);
                        usuarioRef.child("progresso").removeEventListener(this);

                        int novoProgresso = stringProgUsuario[0].intValue();
                        novoProgresso = novoProgresso + 10;

                        usuarioRef.child("progresso").setValue(novoProgresso);
                        usuarioRef.removeEventListener(this);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                invalidaBotao();
            }
        });
    }

    private void showImagens(StorageReference storageRef, final int id, final ImageView img) throws IOException {

        storageRef = storageRef.child("img" + id + ".png");

        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                img.setImageBitmap(bmp);
                img.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }


    private void invalidaBotao() {
        btnConcluirTopico.setEnabled(Boolean.FALSE);
        btnConcluirTopico.setText("Tópico Concluído");
        btnConcluirTopico.setBackgroundColor(getResources().getColor(R.color.colorSecondaryText));
    }
}


