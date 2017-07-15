package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittracker.data.habitContract;
import com.example.android.habittracker.data.habitDbHelper;

import static java.lang.Integer.parseInt;

/**
 * Created by PiotrM on 15.07.2017.
 */

/**
 * Class which handles adding new habits to DB.
 */

public class EditorActivity extends AppCompatActivity {

    private static final String LOG_TAG = EditorActivity.class.getName();

    // Create EditText fields for all of the habit columns.
    private EditText mNameEditText;
    private EditText mTimeEditText;
    private Spinner mInfluenceSpinner;

    private int mInfluence = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mTimeEditText = (EditText) findViewById(R.id.edit_habit_time);
        mInfluenceSpinner = (Spinner) findViewById(R.id.spinner_influence);
        setupSpinner();
    }

    /**
     * Spinner setting with habit influence.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_habit_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mInfluenceSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mInfluenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.influence_positive))) {
                        mInfluence = habitContract.habitEntry.INFLUENCE_POSITIVE;
                    } else if (selection.equals(getString(R.string.influence_negative))) {
                        mInfluence = habitContract.habitEntry.INFLUENCE_NEGATIVE;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mInfluence = 0; // Positive
            }
        });
    }

    private void insertHabit(){
        String nameString = mNameEditText.getText().toString().trim(); // .trim - removes extra spaces from string

        // Find all relevant views that we will need to read user input from

        String timeString = mTimeEditText.getText().toString().trim();
        String influenceString = mInfluenceSpinner.getSelectedItem().toString().trim();


        Integer influenceInteger = 0;
        switch (influenceString){
            case "Positive":
                influenceInteger=0;
                break;
            case "Negative":
                influenceInteger=1;
                break;
        }

        Log.v(LOG_TAG, "----- GENDER ----- : " + influenceInteger.toString());
        Integer timeInteger = parseInt(timeString);


        ContentValues values = new ContentValues();
        values.put(habitContract.habitEntry.COLUMN_HABIT_NAME, nameString);
        values.put(habitContract.habitEntry.COLUMN_HABIT_TIME, timeInteger);
        values.put(habitContract.habitEntry.COLUMN_HABIT_INFLUENCE, influenceInteger);


        // Create and/or open a database to read from it
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        habitDbHelper mDbHelper = new habitDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New row for habit in the DB, returning the ID of that new row.
        long newRowId = db.insert(habitContract.habitEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit added successfully ! " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertHabit();
                //finish activity - return to CatalogActivity
                finish();
                return true;
            case android.R.id.home:
                // Navigate back to parent activity (HabitActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
