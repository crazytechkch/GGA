package co.crazytech.gga.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by eric on 7/29/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DB_NAME = "ggadb";
    public static final int DB_VERSION = 6;

    public DbHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            runFromFile(context,db,"ggadb.sqlite.sql");
            runUpdates(db, 1, DB_VERSION);
        } catch (SQLException | IOException e) {
            Log.e("SQL",e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            /*switch (oldVersion) {
                case 1: runFromFile(context, db, "ggadb.sqlite.2.sql");
                    runFromFile(context, db, "ggadb.sqlite.3.sql");
                    runFromFile(context, db, "ggadb.sqlite.4.sql");
                    runFromFile(context, db, "ggadb.sqlite.5.sql");
                case 2: runFromFile(context, db, "ggadb.sqlite.3.sql");
                    runFromFile(context, db, "ggadb.sqlite.4.sql");
                    runFromFile(context, db, "ggadb.sqlite.5.sql");
                case 3: runFromFile(context, db, "ggadb.sqlite.4.sql");
                    runFromFile(context, db, "ggadb.sqlite.5.sql");
                case 4: runFromFile(context, db, "ggadb.sqlite.5.sql");
            }*/
            runUpdates(db, oldVersion, newVersion);
        } catch (SQLException | IOException e) {
            Log.e("SQL",e.getMessage());
        }
    }

    private void runUpdates(SQLiteDatabase db, int oldVer, int newVer) throws SQLException, IOException{
        for (int i = oldVer; i<newVer; i++) {
            runFromFile(context, db, "ggadb.sqlite."+(i+1)+".sql");
        }
    }

    private void runFromFile(Context context, SQLiteDatabase db, String filename) throws SQLException,IOException {
        InputStream is = context.getAssets().open(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;

        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine())!=null){
            builder.append(line);
        }
        String sqlStr = builder.toString();
        String[] sqls = sqlStr.split(";");
        for (String sql : sqls) {
            Log.d("SQL",sql);
            db.execSQL(sql);
        }
    }

    private void copyDb(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DbHelper.DB_NAME);
        Log.d("DB Copy",is.available()+"");
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer)>0){
            os.write(buffer);
        }
        os.flush();
        os.close();
        is.close();
    }

}
