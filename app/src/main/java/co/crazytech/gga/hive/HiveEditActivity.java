package co.crazytech.gga.hive;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.AgroassetEditActivity;
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
     }
}
