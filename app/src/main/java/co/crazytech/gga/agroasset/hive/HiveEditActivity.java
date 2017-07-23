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
        hive = new Hive(this,extras,"v_hive");
        setAgroasset(hive);
        super.onCreate(savedInstanceState);
        getBtnDone().setOnClickListener(doneListener("agroasset"));
        String farmName = hive.getFarm()!=null?hive.getFarm().getFarmName():"Gaharu Gading";
        String hiveInfo = (hive.getDcode()!=null?hive.getDcode()+" - ":"")+(hive.getNickname()!=null?hive.getNickname():getString(R.string.hive));
        Toast.makeText(this,farmName+"\n"+hiveInfo,Toast.LENGTH_LONG).show();
     }
}
