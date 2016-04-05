package com.zeus.multiuseapp.common.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zeus.multiuseapp.common.Constants;

/**
 * Created by Zeus on 4/4/2016.
 */
public class SQLiteDemo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE "
            + Constants.NOTE_TABLE
            + "("
            + Constants.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.COLUMN_TITLE + " TEXT NOT NULL, "
            + Constants.COLUMN_CONTENT + " TEXT NOT NULL, "
            + Constants.COLUMN_COLOR + " INTEGER NOT NULL, "
            + Constants.COLUMN_MODIFIED_TIME + " INTEGER NOT NULL, "
            + Constants.COLUMN_CREATED_TIME + " INTEGER NOT NULL "
            + ")";

    public SQLiteDemo(Context context) {
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
