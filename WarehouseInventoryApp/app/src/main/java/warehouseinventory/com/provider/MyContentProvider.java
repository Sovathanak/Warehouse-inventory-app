package warehouseinventory.com.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    ItemDatabase itemDB;
    public static final String CONTENT_AUTHORITY = "fit2081.app.sovathanak";
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

//    private static final int MULTIPLE_ITEMS = 1;
//    private static final int SINGLE_ITEMS = 2;
//
//    private static final UriMatcher matcher = createMatcher();
//
//    private static UriMatcher createMatcher(){
//        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(CONTENT_AUTHORITY, Item.tableName, MULTIPLE_ITEMS);
//        uriMatcher.addURI(CONTENT_AUTHORITY, Item.tableName + "/#", SINGLE_ITEMS);
//        return uriMatcher;
//    }


    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        itemDB = ItemDatabase.getDatabase(getContext());
        return true;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Item.tableName);
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);
        cursor = itemDB.getOpenHelper().getReadableDatabase().query(query, selectionArgs);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
