package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by PiotrM on 15.07.2017.
 */

public final class habitContract {

    // Inner table class - for each table in the database
    public static abstract class habitEntry implements BaseColumns {

        //Stores table name
        public final static String TABLE_NAME = "habits";

        //String constants for table name and for each of the headings
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_HABIT_TIME = "time";
        public final static String COLUMN_HABIT_INFLUENCE = "influence";

        //Integer constants for gender
        public final static int INFLUENCE_POSITIVE = 0;
        public final static int INFLUENCE_NEGATIVE = 1;
    }
}
