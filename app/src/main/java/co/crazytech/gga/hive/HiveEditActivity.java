package co.crazytech.gga.hive;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import co.crazytech.gga.R;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class HiveEditActivity extends Activity {
    private EditText etId,etNickname;
    private Hive hive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset_edit);

        QRResult qrres = new QRResult(getIntent().getStringExtra("scanres"));
        hive = new Hive(qrres,this);

        etId = (EditText) findViewById(R.id.editTextId);
        etId.setText(qrres.getType()+hive.getGeoId());
        etNickname = (EditText)findViewById(R.id.editTextNickname);
        etNickname.setText(hive.getNickname());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
