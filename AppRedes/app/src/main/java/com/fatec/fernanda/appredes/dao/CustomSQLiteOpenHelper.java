package com.fatec.fernanda.appredes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fatec.fernanda.appredes.domain.Usuario;

/**
 * Created by Fernanda on 27/08/2016.
 */

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "redes.db";
    private static final int DATABASE_VERSION = 1;

    public CustomSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO Criar tabelas
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO DROP TABLE IF EXISTS ...
    }
}
