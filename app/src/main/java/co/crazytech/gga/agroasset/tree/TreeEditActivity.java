package co.crazytech.gga.agroasset.tree;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.AgroassetEditActivity;
import co.crazytech.gga.agroasset.hive.Hive;
import co.crazytech.gga.db.PersistanceManager;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class TreeEditActivity extends AgroassetEditActivity {
    private Tree tree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        tree = new Tree(this,extras,"v_tree");
        setAgroasset(tree);
        super.onCreate(savedInstanceState);
        getBtnInfuseRec().setVisibility(View.VISIBLE);
        getBtnDone().setOnClickListener(doneListener("agroasset"));
        Toast.makeText(this,"Gaharu Gading - "+getString(R.string.gaharu_tree),Toast.LENGTH_LONG).show();
     }
}
