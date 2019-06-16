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
     * 查询密码
     * @param UserName
     * @return PassWord
     */
    public String queryPassWord(String UserName) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String pass = "";
        Cursor cursor = null;

        try {
            String sql = "select * from " + TBL_NAME + " where username = " + UserName;
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            if (cursor.moveToFirst())
                pass = cursor.getString(cursor.getColumnIndex("password"));
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
        return pass;
    }

    /**
     * 插入用户
     * @param user
     * @param pass 加密后的密码
     * @return
     */
    public long insertUser(String user, String pass) {

        SQLiteDatabase db = helper.getWritableDatabase();
        long ret = 0;

        String sql = "insert into " + TBL_NAME + "(username, password) values(?, ?)";
        SQLiteStatement stat = db.compileStatement(sql);

        db.beginTransaction();
        try {
            stat.bindString(1, user); // user
            stat.bindString(2, pass);
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
     * 更新用户，修改密码用
     * @param user
     * @param pass
     */
    public void updateUser(User user, String pass) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put("username", user.getName());
        vals.put("password", pass);

        try {
            db.update(TBL_NAME, vals, "username = ?", new String[] { user.getName() });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    public int deleteUser(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int ret = 0;
        try {
            ret = db.delete(TBL_NAME, "username = ?", new String[] { user.getName() });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }
        ShowLogE("deleteUser", "DeleteUser: " + ret);
        return ret;
    }

    /**
     * 批量删除用户
     * @param users
     * @return
     */
    public int deleteUsers(List<User> users) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int ret = 0;
        try {
            if (users != null && users.size() > 0) {
                db.beginTransaction();
                try {
                    for (User user : users) {
                        ret += db.delete(TBL_NAME, "username = ?", new String[] { user.getName() });
                    }
                    db.setTransactionSuccessful();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    db.endTransaction();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }
        ShowLogE("deleteUsers", "DeleteUsers: " + ret);
        return ret;
    }
}
