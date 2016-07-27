package co.crazytech.gga.agroasset;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import co.crazytech.gga.R;
import co.crazytech.gga.hive.Hive;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class AgroassetActivity extends Activity {
    private TextView tvId,tvFarm;
    private Agroasset agroasset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset);

        QRResult qrres = new QRResult(getIntent().getStringExtra("scanres"));
        agroasset = new Agroasset(qrres,this);

        tvId = (TextView)findViewById(R.id.textViewId);
        tvId.setText(qrres.getType()+ agroasset.getGeoId()+" "+ agroasset.getNickname());
        tvFarm = (TextView)findViewById(R.id.textViewFarm);
        tvFarm.setText(agroasset.getFarm().getFarmName());


    }

    public Agroasset getAgroasset() {
        return agroasset;
    }

    public void setAgroasset(Agroasset agroasset) {
        this.agroasset = agroasset;
    }
}
