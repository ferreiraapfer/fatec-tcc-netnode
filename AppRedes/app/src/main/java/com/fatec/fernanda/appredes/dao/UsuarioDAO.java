package com.fatec.fernanda.appredes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;
import android.widget.Toast;

import com.fatec.fernanda.appredes.R;
import com.fatec.fernanda.appredes.activities.LoginActivity;
import com.fatec.fernanda.appredes.domain.Usuario;

/**
 * Created by Fernanda on 27/08/2016.
 */

public class UsuarioDAO extends CustomSQLiteOpenHelper {

    public static final String TABLE_NAME = "tbl_usuario";

    public static final String columnId = "usr_id";
    public static final String columnEmail = "usr_email";
    public static final String columNome = "usr_nome";
    public static final String columnSenha = "usr_senha";
    public static final String columnPontuacao = "usr_pontuacao";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + columnId +
            " INTEGER(7) PRIMARY KEY AUTOINCREMENT, " + columnEmail + " TEXT(50) UNIQUE NOT NULL, " + columNome + " TEXT(30) NOT NULL, " +
            columnSenha + " TEXT(45) NOT NULL, " + columnPontuacao + " INTEGER(5) NOT NULL);";

    public UsuarioDAO(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        super.onCreate(sqLiteDatabase);

    }

    public boolean insertUsuario(Usuario user, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] emailArgs = new String[]{user.getEmail()};

        Cursor cur = db.rawQuery("SELECT COUNT("+ columnId + ") FROM " + TABLE_NAME + " WHERE usr_email = ?", emailArgs);

        if (cur.getCount() > 0) {
            Toast.makeText(context, "Email já cadastrado! Faça login", Toast.LENGTH_LONG).show();
            return false;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(columnEmail, user.getEmail());
        contentValues.put(columNome, user.getNome());
        contentValues.put(columnSenha, user.getSenha());
        contentValues.put(columnPontuacao, user.getPontuacao());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Erro no cadastro", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public Usuario login(String[] queryArgs, final Activity activity){

        //TODO Checar se tem a tabela criada ?

        SQLiteDatabase db = this.getReadableDatabase();
        String[] emailArgs = new String[]{queryArgs[0]};

        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE usr_email = ?", emailArgs);

        if (cur.getCount() == 1) {

            //TODO Verificar a senha (criptografada)

            if (queryArgs[1].equals(cur.getString(3))) {
                //while (cur.moveToNext()) {
                int idUsuario = cur.getInt(0);
                String emailUsuario = cur.getString(1);
                String nomeUsuario = cur.getString(2);
                String senhaUsuario = cur.getString(3);
                int pontuacaoUsuario = cur.getInt(4);

                Usuario user = new Usuario(emailUsuario, nomeUsuario, senhaUsuario);
                user.setId(idUsuario);
                user.setPontuacao(pontuacaoUsuario);

                return user;
                //}
            } else {
                //Senha incorreta
                Toast.makeText(activity, "Senha Incorreta!", Toast.LENGTH_LONG).show();
            }
        } else {
            //Usuário não existe
            Toast.makeText(activity, "Usuário não existe!", Toast.LENGTH_LONG).show();
        }
        return null;
    }
}