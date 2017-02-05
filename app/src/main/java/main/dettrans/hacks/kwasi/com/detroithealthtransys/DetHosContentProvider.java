package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by kwas7493 on 1/18/2017.
 */



public class DetHosContentProvider extends ContentProvider {

    public static String AUTHORITY = "com.example.android.searchabledict.DetHosContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/suggester");

    private static final String TAG = DetHosContentProvider.class.getCanonicalName();

    // fields for the database
    static final String ID = "id";
    static final String NAME = "fac_name";
    static final String BIRTHDAY = "address";


    // UriMatcher stuff
    private static final int SEARCH_WORDS = 0;
    private static final int GET_WORD = 1;
    private static final int SEARCH_SUGGEST = 2;
    private static final int REFRESH_SHORTCUT = 3;
    private static final UriMatcher sURIMatcher = buildUriMatcher();

    // MIME types used for searching words or looking up a single definition
    public static final String WORDS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.example.android.searchabledict";
    public static final String DEFINITION_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.example.android.searchabledict";


    private SuggestionDatabase mSuggestDatabase;


    private static UriMatcher buildUriMatcher()
    {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "suggester", SEARCH_WORDS);
        matcher.addURI(AUTHORITY, "suggester/#", GET_WORD);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "", SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT, REFRESH_SHORTCUT);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT + "", REFRESH_SHORTCUT);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        mSuggestDatabase = new SuggestionDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] selectionArgs, String s1) {
        switch (sURIMatcher.match(uri))
        {
            case SEARCH_SUGGEST:
                if (selectionArgs == null)
                {
                    throw new IllegalArgumentException(
                            "selectionArgs must be provided for the Uri: " + uri);
                }
                return getSuggestions(selectionArgs[0]);
            case SEARCH_WORDS:
                if (selectionArgs == null)
                {
                    throw new IllegalArgumentException(
                            "selectionArgs must be provided for the Uri: " + uri);
                }
                return search(selectionArgs[0]);
            case GET_WORD:
                return getWord(uri);
            case REFRESH_SHORTCUT:
                return refreshShortcut(uri);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }


    private Cursor getSuggestions(String query)
    {
        String[] columns = new String[] { BaseColumns._ID, SuggestionOpenHelper.KEY_WORD,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID };

        return mSuggestDatabase.retrieveWordMatches(query, columns);
    }



    private Cursor getWord(Uri uri)
    {
        String rowId = uri.getLastPathSegment();
        String[] columns = new String[]
                { SuggestionOpenHelper.KEY_WORD, SuggestionOpenHelper.SUGGESTION };

        return mSuggestDatabase.retrieveWord(rowId, columns);
    }

    private Cursor search(String query)
    {
        Log.e(TAG, "--> search()");

        query = query.toLowerCase();
        String[] columns = new String[]
                { BaseColumns._ID, SuggestionOpenHelper.KEY_WORD,
                        SuggestionOpenHelper.SUGGESTION};

        return mSuggestDatabase.retrieveWordMatches(query, columns);
    }



    private Cursor refreshShortcut(Uri uri)
    {
        String rowId = uri.getLastPathSegment();
        String[] columns = new String[]
                { BaseColumns._ID, SuggestionOpenHelper.KEY_WORD,
                        SuggestionOpenHelper.SUGGESTION,
                        SearchManager.SUGGEST_COLUMN_SHORTCUT_ID,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID };

        return mSuggestDatabase.retrieveWord(rowId, columns);
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri))
        {
            case SEARCH_WORDS:
                return WORDS_MIME_TYPE;
            case GET_WORD:
                return DEFINITION_MIME_TYPE;
            case SEARCH_SUGGEST:
                return SearchManager.SUGGEST_MIME_TYPE;
            case REFRESH_SHORTCUT:
                return SearchManager.SHORTCUT_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
       return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

}
