package co.crazytech.gga.agroasset;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

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
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        pm.open();
        SQLiteDatabase db = pm.getDb();
        pm.close();
        return null;
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
