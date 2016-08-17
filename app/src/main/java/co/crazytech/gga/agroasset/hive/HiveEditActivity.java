package co.crazytech.gga.agroasset.hive;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.Agroasset;
import co.crazytech.gga.agroasset.AgroassetEditActivity;
import co.crazytech.gga.db.PersistanceManager;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class HiveEditActivity extends AgroassetEditActivity {
    private Hive hive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        hive = new Hive(this,extras,"hive");
        setAgroasset(hive);
        super.onCreate(savedInstanceState);
        Toast.makeText(this,getString(R.string.hive),Toast.LENGTH_LONG).show();
        getBtnDone().setOnClickListener(doneListener("hive"));
     }
}