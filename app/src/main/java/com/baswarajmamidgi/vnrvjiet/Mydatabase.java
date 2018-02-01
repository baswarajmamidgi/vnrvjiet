package com.baswarajmamidgi.vnrvjiet;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by baswarajmamidgi on 20/07/16.
 */


public class Mydatabase {
    private SQLiteDatabase db;
    private final Context context;
    private final Databasehelper dbhelper;
    public Mydatabase(Context c) {
        context = c;
        dbhelper = new Databasehelper(context);
    }

    public long insertMessage(String title,String content) {
        db = dbhelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Databasehelper.TITLE, title);
        contentValues.put(Databasehelper.CONTENT,content);
        contentValues.put(Databasehelper.DATETIME,getDateTime());
        long id=db.insert(Databasehelper.TABLE_NAME,null,contentValues);
        db.close();
        return id;
    }
    public Cursor getMessages()
    {

        SharedPreferences settings = context.getSharedPreferences("settings", 0);
        String order = settings.getString("sort", " DESC");

        db = dbhelper.getWritableDatabase();
        String[] columns={Databasehelper.KEY_ID, Databasehelper.TITLE, Databasehelper.CONTENT, Databasehelper.DATETIME};
        Cursor cursor = db.query(Databasehelper.TABLE_NAME,columns,null,null,null,null, Databasehelper.KEY_ID+order);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }


    public  void rowdelete(String content) {
        db=dbhelper.getWritableDatabase();
        db.delete(Databasehelper.TABLE_NAME, Databasehelper.CONTENT+"=?",new String[]{content});
        db.close();
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM  HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public ArrayList<String> getnotes()
    {
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query(Databasehelper.TABLE_NAME,null,null,null,null,null,null,null);
        cursor.moveToFirst();
        ArrayList<String> arrayList=new ArrayList<>();
        while (!cursor.isAfterLast())
        {
           String name=cursor.getString(cursor.getColumnIndex(Databasehelper.CONTENT));
        arrayList.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;

    }
}
