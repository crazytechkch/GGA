package co.crazytech.gga.agroasset;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.Selection;

import co.crazytech.gga.db.PersistanceManager;
import co.crazytech.gga.util.SelectionBuilder;

/**
 * Created by eric on 12/20/2017.
 */

public class AgroassetProvider extends ContentProvider {
    PersistanceManager pm;

    @Override
    public boolean onCreate() {
        pm = new PersistanceManager(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        pm.open();
        SQLiteDatabase db = pm.getDb();
        SelectionBuilder selBuilder = new SelectionBuilder();
        selBuilder.table(Agroasset.TABLE_NAME).where(selection,selectionArgs);
        Cursor c = selBuilder.query(db,projection,sortOrder);
        Context ctx = getContext();
        assert ctx != null;
        c.setNotificationUri(ctx.getContentResolver(),uri);
        pm.close();
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

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
