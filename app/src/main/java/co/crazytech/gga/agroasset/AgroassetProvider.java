package co.crazytech.gga.agroasset;

import android.content.ContentProvider;
import android.content.ContentUris;
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
        return AgroassetContract.Agroassets.CONTENT_TYPE;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri returnUri;
        pm.open();
        SQLiteDatabase db = pm.getDb();
        Long id = db.insert(AgroassetContract.Agroassets.NAME,null,contentValues);
        pm.close();
        returnUri = ContentUris.withAppendedId(AgroassetContract.Agroassets.CONTENT_URI,id);
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] sArgs) {
        int rows;
        pm.open();
        SQLiteDatabase db = pm.getDb();
        rows = db.delete(AgroassetContract.Agroassets.NAME,s,sArgs);
        pm.close();
        getContext().getContentResolver().notifyChange(uri,null);
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] sArgs) {
        int rows;
        pm.open();
        SQLiteDatabase db = pm.getDb();
        rows = db.update(AgroassetContract.Agroassets.NAME,contentValues,s,sArgs );
        pm.close();
        getContext().getContentResolver().notifyChange(uri,null);
        return rows;
    }
}
