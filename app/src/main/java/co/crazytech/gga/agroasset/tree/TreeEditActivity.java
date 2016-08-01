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
        tree = new Tree(this,extras,"tree");
        setAgroasset(tree);
        super.onCreate(savedInstanceState);
        getBtnInfuseRec().setVisibility(View.VISIBLE);
        getBtnDone().setOnClickListener(doneListener("tree"));
        Toast.makeText(this,getString(R.string.gaharu_tree),Toast.LENGTH_LONG).show();
     }

    @Override
    public View.OnClickListener doneListener(String table) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersistanceManager pm = new PersistanceManager(v.getContext());
                pm.open();
                SQLiteDatabase db = pm.getDb();
                try {
                    tree.setEntityStatusId(getSpnStatus().getSelectedItemId());
                    tree.setFarmId(getSpnFarm().getSelectedItemId());
                    tree.setNickname(getEtNickname().getText().toString());
                    tree.setGeoCol(getEtGeoCol().getText().toString());
                    tree.setGeoRow(getEtGeoRow().getText().toString());
                    tree.setRemark(getEtRemark().getText().toString());
                    if (isRowExists()) {
                        db.execSQL(tree.dbUpdate("tree"));
                    } else {
                        tree.setId(getNewId(db,"tree"));
                        db.execSQL(tree.dbInsert("tree"));
                    }
                    Toast.makeText(v.getContext(),getString(R.string.succes),Toast.LENGTH_LONG).show();
                } catch (SQLException e){
                    Log.e("Agroasset SQL",e.getMessage());
                    Toast.makeText(v.getContext(),getString(R.string.error),Toast.LENGTH_LONG).show();
                }
                pm.close();
                finish();
            }
        };
    }
}
