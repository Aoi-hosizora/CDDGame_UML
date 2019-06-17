package com.oosad.cddgame.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.oosad.cddgame.Data.Entity.Player.User;

import java.util.List;

public class UserDAO {

    private OpenHelper helper;
    private final static String TBL_NAME = "tbl_User";


    public UserDAO(Context context) {
        helper = new OpenHelper(context);
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "UserDAO";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    /**
     * 查询用户
     * @param UserName
     * @return User
     */
    public User queryUser(String UserName) {
        SQLiteDatabase db = helper.getWritableDatabase();

        User user = null;
        Cursor cursor = null;

        try {
            String sql = "select * from " + TBL_NAME + " where username = " + UserName;
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));
                user = new User(username);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return user;
    }

    /**
     * 查询上次登录
     */
    public String queryLast() {
        SQLiteDatabase db = helper.getWritableDatabase();

        String username = "";
        Cursor cursor = null;

        try {
            String sql = "select * from " + TBL_NAME + " where islast = 1";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            if (cursor.moveToFirst())
                username = cursor.getString(cursor.getColumnIndex("username"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return username;
    }

    /**
     * 插入用户
     * @param user
     * @return
     */
    public long insertUser(String user) {

        SQLiteDatabase db = helper.getWritableDatabase();
        long ret = 0;

        String sql = "insert into " + TBL_NAME + "(username) values(?)";
        SQLiteStatement stat = db.compileStatement(sql);

        db.beginTransaction();
        try {
            stat.bindString(1, user); // user
            ret = stat.executeInsert();

            ShowLogE("insertUser", "sql: " + sql + ", ret = " + ret);
            db.setTransactionSuccessful();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return ret;
    }

    /**
     * 更新 IsLast
     * @param username
     */
    public void setIsLast(String username) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put("islast", 0);

        try {
            db.update(TBL_NAME, vals, "", null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }

        db = helper.getWritableDatabase();
        vals = new ContentValues();
        vals.put("islast", 1);

        try {
            db.update(TBL_NAME, vals, "username = ?", new String[] { username });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }
    }
}
