package co.crazytech.gga.agroasset.hive;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

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
public class HiveListActivity extends AgroassetListActivity {
    List<AgroassetGroup> agrogrps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.hive);
        agrogrps = new ArrayList<AgroassetGroup>();
        String sql = "";
        agrogrps.add(new AgroassetGroup(getString(R.string.extraction_pending), R.drawable.sickle,new ArrayList<Agroasset>()));
        agrogrps.add(new AgroassetGroup(getString(R.string.inspection_pending), R.drawable.detective,new ArrayList<Agroasset>()));
        sql = "select id,geo_id,nickname from hive order by date desc";
        agrogrps.add(new AgroassetGroup(getString(R.string.view_all), R.drawable.bee, agroassets(sql)));
        setListAdapter(new AgroassetListAdapter(this,agrogrps));
        setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return childClick(parent,v,groupPosition,childPosition,id);
            }
        });
    }

     public boolean childClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Agroasset asset = agrogrps.get(groupPosition).getAgroassets().get(childPosition);
        Bundle data = new Bundle();
        data.putLong("id",asset.getId());
        data.putLong("geoId",asset.getGeoId());
        data.putString("nickname",asset.getNickname());
        Intent intent = new Intent(this,HiveEditActivity.class);
        intent.putExtras(data);
        startActivity(intent);
        return true;
    }
}
