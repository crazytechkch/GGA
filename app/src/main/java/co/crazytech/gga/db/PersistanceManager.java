package co.crazytech.gga.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by eric on 7/29/2016.
 */
public class PersistanceManager {
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    private Context context;

    public PersistanceManager(Context context) {
        this.context = context;
    }

    public PersistanceManager open() throws SQLException{

        dbHelper = new DbHelper(context,DbHelper.DB_NAME,null,DbHelper.DB_VERSION);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void onCreate(){dbHelper.onCreate(db);}




    public boolean rowExists(String table, String whereClause){
        String query = "SELECT EXISTS(SELECT 1 FROM "+table+(whereClause.length()>0?(" WHERE "+whereClause):"")+" LIMIT 1)";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result==1 ? true:false;
    }

    public boolean pmRowExists(String table, String whereClause){
        open();
        String query = "SELECT EXISTS(SELECT 1 FROM "+table+(whereClause.length()>0?(" WHERE "+whereClause):"")+" LIMIT 1)";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        close();
        return result==1 ? true:false;
    }

    public void close(){dbHelper.close();}

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
