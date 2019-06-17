package com.oosad.cddgame.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "cddgame.db";
    private final static int DB_VERSION = 1;

    public OpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Create_Db_User(db);
    }

    private void Create_Db_User(SQLiteDatabase db) {
        db.execSQL("create table if not exists tbl_User(" +
                "userid integer primary key autoincrement, " +
                "username varchar unique, "+
                "islast smallint default 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
