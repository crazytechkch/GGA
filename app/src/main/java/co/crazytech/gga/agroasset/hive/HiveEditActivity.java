package co.crazytech.gga.agroasset.hive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.AgroassetEditActivity;

/**
 * Created by eric on 7/19/2016.
 */
public class HiveEditActivity extends AgroassetEditActivity {
    private Hive hive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBtnDone().setOnClickListener(doneListener("agroasset"));
        String farmName = hive.getFarm()!=null?hive.getFarm().getFarmName():"Gaharu Gading";
        String hiveInfo = (hive.getDcode()!=null?hive.getDcode()+" - ":"")+(hive.getNickname()!=null?hive.getNickname():getString(R.string.hive));
        Toast.makeText(this,farmName+"\n"+hiveInfo,Toast.LENGTH_LONG).show();
     }

    @Override
    protected void initAgroasset() {
        Bundle extras = getIntent().getExtras();
        hive = new Hive(this,extras,"v_hive");
        setAgroasset(hive);
    }

    @Override
    public View.OnClickListener btnExtractListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getAgroasset().getId()!=null){
                    Intent intent = new Intent(v.getContext(),HiveExtractActivity.class);
                    intent.putExtra("agroassetId",getAgroasset().getId());
                    intent.putExtra("nickname",getAgroasset().getNickname());
                    intent.putExtra("dcode",getAgroasset().getDcode());
                    intent.putExtra("code",getAgroasset().getCode());
                    startActivityForResult(intent,REQ_EXTRACT);
                }

            }
        };
    }
}
