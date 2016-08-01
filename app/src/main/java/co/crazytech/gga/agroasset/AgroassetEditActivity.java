package co.crazytech.gga.agroasset;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.hive.Hive;
import co.crazytech.gga.db.PersistanceManager;
import co.crazytech.gga.zbar.QRResult;
import ctcommons.SimpleCustomAdapter;
import ctcommons.SimpleObject;

/**
 * Created by eric on 7/19/2016.
 */
public class AgroassetEditActivity extends AppCompatActivity{
    private EditText etId,etNickname,etGeoCol,etGeoRow;
    private ImageButton btnInspectRec,btnExtractRec,btnInfuseRec;
    private Button btnDone;
    private Spinner spnFarm,spnStatus;
    private Agroasset agroasset;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset_edit);

        QRResult qrres = new QRResult(getIntent().getStringExtra("scanres"));
        agroasset = new Hive(qrres,this);

        etNickname = (EditText)findViewById(R.id.editTextNickname);
        etNickname.setText(agroasset.getNickname());
        etGeoCol = (EditText)findViewById(R.id.editTextColumn);
        etGeoRow = (EditText)findViewById(R.id.editTextRow);

        spnFarm = (Spinner)findViewById(R.id.spinnerFarm);
        spnFarm.setAdapter(farmAdapter());

        spnStatus = (Spinner)findViewById(R.id.spinnerStatus);
        spnStatus.setAdapter(statusAdapter());

        btnInspectRec = (ImageButton)findViewById(R.id.buttonInspectRec);
        btnInspectRec.setEnabled(false);
        btnExtractRec = (ImageButton)findViewById(R.id.buttonExtractRec);
        btnExtractRec.setEnabled(false);
        btnInfuseRec = (ImageButton)findViewById(R.id.buttonInfuseRec);
        btnInfuseRec.setEnabled(false);

        btnDone = (Button)findViewById(R.id.buttonDone);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private SimpleCustomAdapter farmAdapter() {
        List<SimpleObject> simObjs = new ArrayList<SimpleObject>();
        PersistanceManager pm = new PersistanceManager(this);
        String sql = "select id,farmname from farm order by farmname asc";
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try {
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                simObjs.add(new SimpleObject(cursor.getLong(0),cursor.getString(1)));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("SQL Exception",e.getMessage());
        } finally {
            pm.close();
        }
        SimpleCustomAdapter adapter = new SimpleCustomAdapter(this, simObjs);
        return adapter;
    }
    private SimpleCustomAdapter statusAdapter() {
        List<SimpleObject> simObjs = new ArrayList<SimpleObject>();
        PersistanceManager pm = new PersistanceManager(this);
        String sql = "select id,status_name from entity_status";
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try {
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                simObjs.add(new SimpleObject(cursor.getLong(0),cursor.getString(1)));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("SQL Exception",e.getMessage());
        } finally {
            pm.close();
        }
        SimpleCustomAdapter adapter = new SimpleCustomAdapter(this, simObjs);
        return adapter;
    }

    public Long getNewId(SQLiteDatabase db, String assetTable) {
        Cursor cursor = db.rawQuery("select max(id) from "+assetTable,null);
        cursor.moveToFirst();
        Long maxId = cursor.getLong(0);
        cursor.close();
        return maxId+1;
    }

    public Agroasset getAgroasset() {
        return agroasset;
    }

    public ImageButton getBtnInspectRec() {
        return btnInspectRec;
    }

    public ImageButton getBtnExtractRec() {
        return btnExtractRec;
    }

    public ImageButton getBtnInfuseRec() {
        return btnInfuseRec;
    }

    public Button getBtnDone() {
        return btnDone;
    }

    public void setAgroasset(Agroasset agroasset) {
        this.agroasset = agroasset;
    }

    public View.OnClickListener doneListener(final String table){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDone(table);
            }
        };
    }

    private void onDone(String assetTable) {
        PersistanceManager pm = new PersistanceManager(this);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        boolean rowExists = pm.rowExists(assetTable,"id="+getAgroasset().getId());
        try {
            if (rowExists) {
                db.execSQL(getAgroasset().dbUpdate(assetTable));
            } else {
                getAgroasset().setId(getNewId(db,assetTable));
                getAgroasset().setEntityStatusId(spnStatus.getSelectedItemId());
                getAgroasset().setFarmId(spnFarm.getSelectedItemId());
                getAgroasset().setNickname(etNickname.getText().toString());
                getAgroasset().setGeoCol(etGeoCol.getText().toString());
                db.execSQL(getAgroasset().dbInsert(assetTable));
            }
        } catch (SQLException e){
            Log.e("Agroasset SQL",e.getMessage());
        }
        pm.close();
        finish();
    }

    public static class TYPE {
        public static final int TREE = 1;
        public static final int HIVE = 2;
    }
}
