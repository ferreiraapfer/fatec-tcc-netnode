package com.fatec.fernanda.appredes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;
import android.widget.Toast;
import com.fatec.fernanda.appredes.domain.Usuario;
import java.lang.Object;
import android.content.Context;
import java.io.FileOutputStream;

/**
 * Created by Fernanda on 27/08/2016.
 */

public class UsuarioDAO extends CustomSQLiteOpenHelper {

    public static final String TABLE_NAME = "tbl_usuario";

    public static final String columnId = "usr_id";
    public static final String columnEmail = "usr_email";
    public static final String columnNome = "usr_nome";
    public static final String columnSenha = "usr_senha";
    public static final String columnPontuacao = "usr_pontuacao";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + columnId +
            " INTEGER(7) PRIMARY KEY AUTOINCREMENT, " + columnEmail + " TEXT(50) UNIQUE NOT NULL, " + columnNome + " TEXT(30) NOT NULL, " +
            columnSenha + " TEXT(45) NOT NULL, " + columnPontuacao + " INTEGER(5) NOT NULL);";

    public UsuarioDAO(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        super.onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    public boolean insertUsuario(Usuario user, Context context) {

        String fileName = "userFile";
        String[] content = {user.getNome(), String.valueOf(user.getPontuacao())};

        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content[0].getBytes());
            outputStream.write(content[1].getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        /*

        SQLiteDatabase db = this.getWritableDatabase();

         String[] emailArgs = new String[]{user.getEmail()};

        Cursor cur = db.rawQuery("SELECT COUNT("+ columnId + ") FROM " + TABLE_NAME + " WHERE usr_email = ?", emailArgs);

        if (cur.getCount() > 0) {
            Toast.makeText(context, "Email já cadastrado! Faça login", Toast.LENGTH_LONG).show();
            return false;
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(columnEmail, user.getEmail());
        contentValues.put(columnNome, user.getNome());
        contentValues.put(columnSenha, user.getSenha());
        contentValues.put(columnPontuacao, user.getPontuacao());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Erro no cadastro", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

        */
    }
}