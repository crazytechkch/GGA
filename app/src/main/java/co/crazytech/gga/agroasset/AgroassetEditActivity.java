package co.crazytech.gga.agroasset;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import co.crazytech.gga.R;
import co.crazytech.gga.hive.Hive;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class AgroassetEditActivity extends Activity {
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public Agroasset getAgroasset() {
        return agroasset;
    }

    public void setAgroasset(Agroasset agroasset) {
        this.agroasset = agroasset;
    }
}
