package co.crazytech.gga.hive;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import co.crazytech.gga.R;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class HiveActivity extends Activity {
    private TextView tvId,tvFarm;
    private Hive hive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hive);

        QRResult qrres = new QRResult(getIntent().getStringExtra("scanres"));
        hive = new Hive(qrres,this);

        tvId = (TextView)findViewById(R.id.textViewId);
        tvId.setText(qrres.getType()+hive.getGeoId()+" "+hive.getNickname());

        tvFarm = (TextView)findViewById(R.id.textViewFarm);
        tvFarm.setText(hive.getFarm().getFarmName());
    }
}
