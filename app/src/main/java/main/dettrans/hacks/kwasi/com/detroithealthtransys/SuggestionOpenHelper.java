package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kwas7493 on 1/19/2017.
 */

public class SuggestionOpenHelper extends SQLiteOpenHelper {



    private static final String TAG = SuggestionOpenHelper.class.getClass().getCanonicalName();
    // database declarations

    public static final String VIRTUAL_TABLE = "VirtualTable";
    private SQLiteDatabase database;
    private Context helperContext;
    static final String DATABASE_NAME = "suggested";
    static final String TABLE_NAME = "hospitalNameTable";
    static final int DATABASE_VERSION = 8;

    // The columns to include in the suggesetions table
    public static final String KEY_WORD = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String SUGGESTION = SearchManager.SUGGEST_COLUMN_TEXT_2;

    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_WORD+" TEXT NOT NULL, " +
                    SUGGESTION+" TEXT NOT NULL);";
/*

    private static final String FTS_TABLE_CREATE = "CREATE VIRTUAL TABLE "
            + TABLE_NAME + " USING fts3 (" + KEY_WORD + ", "
            + SUGGESTION + ");";*/



    SuggestionOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        helperContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        database = sqLiteDatabase;
        database.execSQL(CREATE_TABLE);
        loadSuggestionsDictionary();
    }

    private void loadSuggestionsDictionary() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadSuggestions();
                }catch (IOException ioException){
                    throw new RuntimeException(ioException);
                }

            }
        }).start();
    }

    private void loadSuggestions() throws IOException {
        InputStream inputStream = helperContext.getResources().openRawResource(R.raw.searchable_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            String lineToRead;

            while((lineToRead = reader.readLine()) != null){
                String[] stringArray = TextUtils.split(lineToRead, "-");
                if(stringArray.length > 2)
                    continue;
                long id = addSuggestion(stringArray[0].trim(), stringArray[1].trim());
                Log.e("Row id", String.valueOf(id));
                if(id < 0){
                    Log.e(TAG, "unable to add word: " + stringArray[0].trim());
                }
                addSuggestion("���", "˫��������к�");
            }
        }finally {
            //prevent resource leaks
            reader.close();

        }

    }

    private long addSuggestion(String keyword, String key_suggestion) {
        ContentValues firstValues = new ContentValues();
        firstValues.put(KEY_WORD, keyword);
        firstValues.put(SUGGESTION, key_suggestion);
        return database.insert(TABLE_NAME, null, firstValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



}
