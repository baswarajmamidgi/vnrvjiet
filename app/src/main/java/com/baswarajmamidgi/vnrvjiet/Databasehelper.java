package com.baswarajmamidgi.vnrvjiet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by baswarajmamidgi on 20/07/16.
 */
public class Databasehelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="DataStorage";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="NotificationsTable";
    public static final String TITLE="title";

    public static final String CONTENT="content";
    public static final String KEY_ID="_id";
    public static final String DATETIME="date";

    private static final String CREATE_TABLE="create table "+ TABLE_NAME+" ("+
            KEY_ID+" integer primary key autoincrement, " + TITLE+" text not null, "+ CONTENT+" text not null, "+ DATETIME+" text not null);";


    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
