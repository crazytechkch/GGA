package co.crazytech.gga.agroasset.tree;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.AgroassetEditActivity;
import co.crazytech.gga.agroasset.hive.Hive;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class TreeEditActivity extends AgroassetEditActivity {
    private Tree tree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        QRResult qrres = new QRResult(getIntent().getStringExtra("scanres"));
        tree = new Tree(qrres,this);
        setAgroasset(tree);
        super.onCreate(savedInstanceState);
        getBtnInfuseRec().setVisibility(View.VISIBLE);
        getBtnDone().setOnClickListener(doneListener("tree"));
        Toast.makeText(this,getString(R.string.gaharu_tree),Toast.LENGTH_LONG).show();
     }


}
