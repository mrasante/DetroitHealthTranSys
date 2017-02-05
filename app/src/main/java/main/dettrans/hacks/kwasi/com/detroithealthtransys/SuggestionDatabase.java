package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import java.util.HashMap;

/**
 * Created by kwas7493 on 1/19/2017.
 */

public class SuggestionDatabase {

    private static final String TAG = SuggestionDatabase.class.getCanonicalName();
    private final SuggestionOpenHelper mSuggestionOpenHelper;
    private static final HashMap<String, String> columnHashMap = createColumnHashMap();


    public SuggestionDatabase(Context context) {
        mSuggestionOpenHelper = new SuggestionOpenHelper(context);
    }

    private static HashMap<String, String> createColumnHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(SuggestionOpenHelper.KEY_WORD, SuggestionOpenHelper.KEY_WORD);
        hashMap.put(SuggestionOpenHelper.SUGGESTION, SuggestionOpenHelper.SUGGESTION);

        hashMap.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        hashMap.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        hashMap.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid AS " + SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);

        return hashMap;
    }

    /**
     * @param rowId
     * @param columns
     * @return
     */

    public Cursor retrieveWord(String rowId, String[] columns) {
        String selection = "rowid = ?";
        String[] selectionArguments = new String[]{rowId};
        return query(selection, selectionArguments, columns);

    }


    private Cursor query(String selection, String[] selectionArguments, String[] columns) {
        SQLiteQueryBuilder sqlQueryBuilder = new SQLiteQueryBuilder();
        sqlQueryBuilder.setTables(SuggestionOpenHelper.TABLE_NAME);
        sqlQueryBuilder.setProjectionMap(columnHashMap);
        SQLiteDatabase database = mSuggestionOpenHelper.getWritableDatabase();
        Cursor cursor = sqlQueryBuilder.query(database, columns, selection, selectionArguments, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }


    public Cursor retrieveWordMatches(String query, String[] columns) {
        String selection = SuggestionOpenHelper.KEY_WORD+ " LIKE ?";
        String[] selectionArgs = new String[]
                {"%" + query + "%"};

        return query(selection, selectionArgs, columns);

    }

}
