package co.crazytech.gga.agroasset.hive;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import co.crazytech.gga.R;
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
        QRResult qrres = new QRResult(getIntent().getStringExtra("scanres"));
        hive = new Hive(qrres,this);
        setAgroasset(hive);
        super.onCreate(savedInstanceState);
        Toast.makeText(this,getString(R.string.hive),Toast.LENGTH_LONG).show();
        getBtnDone().setOnClickListener(doneListener("hive"));
     }
}
