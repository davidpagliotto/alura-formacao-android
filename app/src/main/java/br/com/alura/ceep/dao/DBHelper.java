package br.com.alura.ceep.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "ceep.sqlite";
    private static int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "" +
                " CREATE TABLE nota ( " +
                "  id CHAR(36) PRIMARY KEY, " +
                "  titulo TEXT, " +
                "  descricao TEXT, " +
                "  cor TEXT, " +
                "  posicao INTEGER " +
                " )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
