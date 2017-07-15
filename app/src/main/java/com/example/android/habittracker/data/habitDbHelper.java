package com.example.android.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.habittracker.data.habitContract.habitEntry.TABLE_NAME;

/**
 * Created by PiotrM on 15.07.2017.
 */

public class habitDbHelper extends SQLiteOpenHelper {

    // if you change the database schema you must increment the DB version
    // Create contstants for database name and version - version by convention starts at one
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Habit.db";
    public final static String SQL_CREATE_ENTRIES = "CREATE TABLE "+ TABLE_NAME + " (" + habitContract.habitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + habitContract.habitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, " + habitContract.habitEntry.COLUMN_HABIT_TIME + " INTEGER NOT NULL, " + habitContract.habitEntry.COLUMN_HABIT_INFLUENCE + " INTEGER NOT NULL);";
    public final static String SQL_DELETE_ENTRIES = "DROP TABLE "+ TABLE_NAME ;

    // Constructor
    public habitDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // implement the onCreate method - used for creation of DB
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // implement onUpgrade method - is for the DB schema when DB changes
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // implement onDowngrade method
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

}
