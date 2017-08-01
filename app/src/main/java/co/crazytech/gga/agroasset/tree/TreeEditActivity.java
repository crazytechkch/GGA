package co.crazytech.gga.agroasset.tree;

import android.content.Intent;
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
import co.crazytech.gga.agroasset.hive.HiveExtractActivity;
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
        String farmName = tree.getFarm()!=null?tree.getFarm().getFarmName():"Gaharu Gading";
        String treeInfo = (tree.getDcode()!=null?tree.getDcode()+" - ":"")+(tree.getNickname()!=null?tree.getNickname():getString(R.string.gaharu_tree));
        Toast.makeText(this,farmName+"\n"+treeInfo,Toast.LENGTH_LONG).show();
     }

    @Override
    public View.OnClickListener btnExtractListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getAgroasset().getId()!=null){
                    Intent intent = new Intent(v.getContext(),TreeExtractActivity.class);
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
