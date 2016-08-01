package co.crazytech.gga.agroasset;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import co.crazytech.gga.R;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class AgroassetActivity extends AppCompatActivity {
    private TextView tvId,tvFarm;
    private Agroasset agroasset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset);

        QRResult qrres = new QRResult(getIntent().getStringExtra("scanres"));
        agroasset = new Agroasset(qrres,this);

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
