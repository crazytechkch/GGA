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
import android.widget.TextView;
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
    private EditText etId,etNickname,etRemark;
    private ImageButton btnInspectRec,btnExtractRec,btnInfuseRec;
    private Button btnDone;
    private Spinner spnFarm,spnStatus;
    private Agroasset agroasset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset_edit);

        etId = (EditText)findViewById(R.id.editTextDcode);
        etId.setText(agroasset.getDcode());
        etNickname = (EditText)findViewById(R.id.editTextNickname);
        etNickname.setText(agroasset.getNickname());
        etRemark= (EditText)findViewById(R.id.editTextRemark);
        etRemark.setText(agroasset.getRemark());

        TextView tvId = (TextView)findViewById(R.id.textViewId);
        tvId.setText(agroasset.getCode()!=null?agroasset.getCode():"NOCODE");

        spnFarm = (Spinner)findViewById(R.id.spinnerFarm);
        spnFarm.setAdapter(farmAdapter());

        spnStatus = (Spinner)findViewById(R.id.spinnerStatus);
        spnStatus.setAdapter(statusAdapter());

        btnInspectRec = (ImageButton)findViewById(R.id.buttonInspectRec);
        if (!isRowExists()) btnInspectRec.setEnabled(false);
        btnExtractRec = (ImageButton)findViewById(R.id.buttonExtractRec);
        if (!isRowExists()) btnExtractRec.setEnabled(false);
        btnInfuseRec = (ImageButton)findViewById(R.id.buttonInfuseRec);
        if (!isRowExists()) btnInfuseRec.setEnabled(false);

        btnDone = (Button)findViewById(R.id.buttonDone);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private SimpleCustomAdapter farmAdapter() {
        List<SimpleObject> simObjs = new ArrayList<SimpleObject>();
        PersistanceManager pm = new PersistanceManager(this);
        String sql = "select id,name from farm order by name asc";
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

    public boolean isRowExists() {
        PersistanceManager pm = new PersistanceManager(this);
        if(agroasset.getProdTypeId()==2)return pm.pmRowExists("v_hive","id = "+agroasset.getId());
        if(agroasset.getProdTypeId()==1)return pm.pmRowExists("v_tree","id = "+agroasset.getId());
        return false;
    }

    private void onDone(String assetTable) {
        PersistanceManager pm = new PersistanceManager(this);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try {
            getAgroasset().setEntityStatusId(spnStatus.getSelectedItemId());
            getAgroasset().setFarmId(spnFarm.getSelectedItemId());
            getAgroasset().setNickname(etNickname.getText().toString());
            getAgroasset().setRemark(etRemark.getText().toString());
            getAgroasset().setDcode(etId.getText().toString());
            if (isRowExists()) {
                db.execSQL(getAgroasset().dbUpdate(assetTable));
            } else {
                getAgroasset().setId(getNewId(db,assetTable));
                db.execSQL(getAgroasset().dbInsert(assetTable));
            }
            Toast.makeText(this,getString(R.string.succes),Toast.LENGTH_LONG).show();
        } catch (SQLException e){
            Log.e("Agroasset SQL",e.getMessage());
            Toast.makeText(this,getString(R.string.error),Toast.LENGTH_LONG).show();
        }
        pm.close();
        finish();
    }

    public EditText getEtId() {
        return etId;
    }

    public EditText getEtNickname() {
        return etNickname;
    }

    public EditText getEtRemark() {
        return etRemark;
    }

    public Spinner getSpnFarm() {
        return spnFarm;
    }

    public Spinner getSpnStatus() {
        return spnStatus;
    }

    public static class TYPE {
        public static final int TREE = 1;
        public static final int HIVE = 2;
    }
}
