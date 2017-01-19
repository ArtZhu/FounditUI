package com.cuilinchen.mappart.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.cuilinchen.mappart.user.User;

import java.util.ArrayList;

/**
 * Created by CUILINCHEN on 5/9/16.
 */
public class UserDBManager {
  private SQLiteDatabase db;
  private Cursor cursor = null;
  private String database_name;

  private Context context;

  public static final String PhoneDBName = "PhoneDB";

  public UserDBManager(Context _context, String _database_name) {
    context = _context;
    database_name = _database_name;
  }

  public void createTable(String database_name) {
    db = context.openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null);
    db.execSQL("CREATE TABLE IF NOT EXISTS " +
        database_name +
        " (USERNAME VARCHAR, " +
        "  PASSWORD VARCHAR, " +
        "  RESID INT);");
  }

  public void deleteTable() {
    context.deleteDatabase(database_name);
  }


  public void deleteData(User user) {
    db.delete(database_name, "USERNAME=?", new String[]{user.email});
  }

  public void insertData(User user) {
    ContentValues values = new ContentValues();
    values.put("USERNAME", user.email);
    values.put("PASSWORD", user.password);
    values.put("RESID", user.img);
    db.insert(database_name, null, values);
  }

  public ArrayList<User> retrieveList() {
    final ArrayList<User> results = new  ArrayList<User>();
    //lists all records
    cursor = db.rawQuery("SELECT USERNAME, PASSWORD, RESID FROM " +
        database_name, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        do {
          String username = cursor.getString(cursor.getColumnIndex("USERNAME"));
          String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
          int resid = cursor.getInt(cursor.getColumnIndex("RESID"));
          User u = new User(username, password, resid);
          results.add(u);
        } while (cursor.moveToNext());
      }
      cursor.close();
    }
    return results;
  }


  public static boolean exist(Context context, String DBName) {
    SQLiteDatabase db = null;
    try {
      db = SQLiteDatabase.openDatabase(DBName, null,
          SQLiteDatabase.OPEN_READONLY);
      db.close();
    } catch (SQLiteException e) {
      // database doesn't exist yet.
    }
    return db != null;
  }
}
