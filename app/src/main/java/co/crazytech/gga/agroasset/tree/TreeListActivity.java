package co.crazytech.gga.agroasset.tree;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import java.util.ArrayList;
import java.util.List;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.Agroasset;
import co.crazytech.gga.agroasset.AgroassetGroup;
import co.crazytech.gga.agroasset.AgroassetListActivity;
import co.crazytech.gga.agroasset.AgroassetListAdapter;
import co.crazytech.gga.db.PersistanceManager;

/**
 * Created by eric on 7/28/2016.
 */
public class TreeListActivity extends AgroassetListActivity {
    private List<AgroassetGroup> agrogrps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.gaharu_tree);
        agrogrps = new ArrayList<AgroassetGroup>();
        String sql = "";
//        agrogrps.add(new AgroassetGroup(getString(R.string.extraction_pending), R.drawable.sickle,new ArrayList<Agroasset>()));
//        agrogrps.add(new AgroassetGroup(getString(R.string.inspection_pending), R.drawable.detective,new ArrayList<Agroasset>()));
//        agrogrps.add(new AgroassetGroup(getString(R.string.infusion_pending), R.drawable.syringe,new ArrayList<Agroasset>()));
        PersistanceManager pm = new PersistanceManager(this);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        Cursor cursor = db.rawQuery("select id,status_name from entity_status",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Long id = cursor.getLong(0);
            String statName = cursor.getString(1);
            if (id<=2||id>=6){
                sql = "select id,nickname,code,dcode,CAST(dcode as SIGNED) AS casted_column from v_tree where entity_status_id = "+id+" order by casted_column asc, dcode asc";
                agrogrps.add(new AgroassetGroup(statName.toUpperCase(),R.drawable.tree,agroassets(sql)));
            }
            cursor.moveToNext();
        }
        cursor.close();
        pm.close();
        sql = "select id,nickname,code,dcode,CAST(dcode as SIGNED) AS casted_column from v_tree order by casted_column asc, dcode asc";
        agrogrps.add(new AgroassetGroup(getString(R.string.view_all), R.drawable.tree,agroassets(sql)));
        setListAdapter(new AgroassetListAdapter(this,agrogrps));
        setOnChildClickListener(childClickListener());
    }

    private OnChildClickListener childClickListener(){
        return new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Agroasset asset = agrogrps.get(groupPosition).getAgroassets().get(childPosition);
                Bundle data = new Bundle();
                data.putLong("id",asset.getId());
                data.putString("nickname",asset.getNickname());
                data.putLong("prodTypeId",1);
                Intent intent = new Intent(parent.getContext(),TreeEditActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                return false;
            }
        };
    }
}
