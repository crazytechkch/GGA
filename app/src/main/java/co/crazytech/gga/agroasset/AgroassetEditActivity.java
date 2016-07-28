package co.crazytech.gga.agroasset;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.hive.Hive;
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

        ImageButton btnInspectRec = (ImageButton)findViewById(R.id.buttonInspectRec);
        btnInspectRec.setEnabled(false);
        ImageButton btnExtractRec = (ImageButton)findViewById(R.id.buttonExtractRec);
        btnExtractRec.setEnabled(false);
        ImageButton btnInfuseRec = (ImageButton)findViewById(R.id.buttonInfuseRec);
        btnInfuseRec.setEnabled(false);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public Agroasset getAgroasset() {
        return agroasset;
    }

    public void setAgroasset(Agroasset agroasset) {
        this.agroasset = agroasset;
    }
}
