package com.cuilinchen.mappart.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.cuilinchen.mappart.user.User;

import java.util.ArrayList;

/**
 * Created by Art on 5/12/16.
 */
public class SettingDBManager {
  private static SQLiteDatabase db;
  private static Cursor cursor = null;

  //private static Context context;

  public static final String SettingDBName = "SettingDB";

  private static String database_name = SettingDBName;

  //public SettingDBManager(Context _context, String _database_name) {
    //context = _context;
    //database_name = _database_name;
  //}

  public static void createTable(Context context) {
    db = context.openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null);
    db.execSQL("CREATE TABLE IF NOT EXISTS " +
        database_name +
        " (SETTING VARCHAR, " +
        "  CHOICE VARCHAR);");
  }

  public static void deleteTable(Context context) {
    context.deleteDatabase(database_name);
  }


  public static void deleteData(String setting) {
    db.delete(database_name, "SETTING=?", new String[]{setting});
  }

  public static void insertData(String setting, String choice) {
    ContentValues values = new ContentValues();
    values.put("SETTING", setting);
    values.put("CHOICE", choice);
    db.insert(database_name, null, values);
  }

  public static ArrayList<String> retrieveList() {
    final ArrayList<String> results = new ArrayList<>();
    //lists all records
    cursor = db.rawQuery("SELECT SETTING, CHOICE FROM " +
        database_name, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        do {
          String setting = cursor.getString(cursor.getColumnIndex("SETTING"));
          String choice = cursor.getString(cursor.getColumnIndex("CHOICE"));
          results.add(setting + ":" + choice);
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
