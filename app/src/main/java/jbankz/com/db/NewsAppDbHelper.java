package jbankz.com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by King Jaycee on 30/10/2017.
 */

public class NewsAppDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "NewsAppDbHelper";

    public static final String DATABASE_NAME = "newsapp.db";

    public static final int DATABASE_VERSION = 1;


    public NewsAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String NEWS_TABLE =

                "CREATE TABLE " + DbContract.DbEntry.NEWS_TABLE_NAME + " (" +

                        DbContract.DbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbContract.DbEntry.NEWS_AUTHOR + " TEXT NOT NULL," +
                        DbContract.DbEntry.NEWS_DESCRIPTION + " TEXT NOT NULL, " +
                        DbContract.DbEntry.NEWS_URL + " TEXT NOT NULL, " +
                        DbContract.DbEntry.NEWS_IMAGE_URL + " TEXT NOT NULL, " +
                        " UNIQUE (" + DbContract.DbEntry.NEWS_PUBLISHED_AT + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(NEWS_TABLE);

        Log.v(TAG, "Database created Successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + DbContract.DbEntry.NEWS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
