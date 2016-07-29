package co.crazytech.gga.agroasset;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.hive.Hive;
import co.crazytech.gga.db.PersistanceManager;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class AgroassetEditActivity extends AppCompatActivity{
    private EditText etId,etNickname;
    private Agroasset agroasset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset_edit);

        QRResult qrres = new QRResult(getIntent().getStringExtra("scanres"));
        agroasset = new Hive(qrres,this);

        etId = (EditText) findViewById(R.id.editTextId);
        etId.setText(qrres.getType()+agroasset.getGeoId());
        etNickname = (EditText)findViewById(R.id.editTextNickname);
        etNickname.setText(agroasset.getNickname());

        Spinner spnFarm = (Spinner)findViewById(R.id.spinnerFarm);
        spnFarm.setAdapter(spnFarmAdapter());

        ImageButton btnInspectRec = (ImageButton)findViewById(R.id.buttonInspectRec);
        btnInspectRec.setEnabled(false);
        ImageButton btnExtractRec = (ImageButton)findViewById(R.id.buttonExtractRec);
        btnExtractRec.setEnabled(false);
        ImageButton btnInfuseRec = (ImageButton)findViewById(R.id.buttonInfuseRec);
        btnInfuseRec.setEnabled(false);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private ArrayAdapter<String> spnFarmAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PersistanceManager pm = new PersistanceManager(this);
        String sql = "select id,farmname from farm order by farmname asc";
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try {
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                adapter.add(cursor.getString(1));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("SQL Exception",e.getMessage());
        } finally {
            pm.close();
        }

        return adapter;
    }

    public Agroasset getAgroasset() {
        return agroasset;
    }

    public void setAgroasset(Agroasset agroasset) {
        this.agroasset = agroasset;
    }
}
