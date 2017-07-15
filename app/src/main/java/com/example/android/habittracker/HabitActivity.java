package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.habitContract;
import com.example.android.habittracker.data.habitDbHelper;

public class HabitActivity extends AppCompatActivity {

    private static final String LOG_TAG = EditorActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        readCursor(displayDatabaseInfo());
    }

    // method which allows to display actual Table contents after update.
    private Cursor displayDatabaseInfo() {
        habitDbHelper mDbHelper = new habitDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] project = {
                habitContract.habitEntry._ID,
                habitContract.habitEntry.COLUMN_HABIT_NAME,
                habitContract.habitEntry.COLUMN_HABIT_TIME,
                habitContract.habitEntry.COLUMN_HABIT_INFLUENCE
        };

        Cursor cursor = db.query(habitContract.habitEntry.TABLE_NAME, project, null, null, null, null, null);

        return cursor;
    }

    private void readCursor(Cursor cursor) {
        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("You have stored :  " + cursor.getCount() + " habits so far.\n\n");

            int idColumnIndex = cursor.getColumnIndex(habitContract.habitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(habitContract.habitEntry.COLUMN_HABIT_NAME);
            int timeColumnIndex = cursor.getColumnIndex(habitContract.habitEntry.COLUMN_HABIT_TIME);
            int influenceColumnIndex = cursor.getColumnIndex(habitContract.habitEntry.COLUMN_HABIT_INFLUENCE);

            while (cursor.moveToNext()) {

                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentTime = cursor.getString(timeColumnIndex);
                String currentInfluence = cursor.getString(influenceColumnIndex);
                displayView.append("\n ID: " + currentId + " - Habit : " + currentName + " - Time :" + currentTime + " min - Influence : " + currentInfluence);
            }
        } finally {
            cursor.close();
        }
    }

}

