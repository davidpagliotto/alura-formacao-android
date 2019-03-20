package br.com.alura.ceep.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BaseDAO {

    protected SQLiteDatabase db;

    public BaseDAO(Context context) {
        this.db = new DBHelper(context).getWritableDatabase();
    }

}
