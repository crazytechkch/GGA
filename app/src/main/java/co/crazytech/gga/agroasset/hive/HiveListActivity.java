package co.crazytech.gga.agroasset.hive;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

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
//        agrogrps.add(new AgroassetGroup(getString(R.string.extraction_pending), R.drawable.sickle,new ArrayList<Agroasset>()));
//        agrogrps.add(new AgroassetGroup(getString(R.string.inspection_pending), R.drawable.detective,new ArrayList<Agroasset>()));
        String sql = "";
        PersistanceManager pm = new PersistanceManager(this);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        Cursor totalCurs = db.rawQuery("select count(id) from v_hive",null);
        totalCurs.moveToFirst();
        int count = totalCurs.getInt(0);
        totalCurs.close();
        TextView tvTotal = (TextView)findViewById(R.id.textViewTotal);
        tvTotal.setText("Total records : "+count);
        Cursor cursor = db.rawQuery("select id,status_name from entity_status",null);
        cursor.moveToFirst();
        sql = "select a.id,a.nickname,a.code,a.dcode,max(e.date) as maxdate,(julianday('now')-julianday(e.date)) as datediff,CAST(a.dcode as SIGNED) AS casted_column " +
                "from v_hive a left join v_extract_hive e on a.id = e.agroasset_id " +
                "where datediff <= 30  group by a.id order by datediff";
        agrogrps.add(new AgroassetGroup(getString(R.string.recent_harvest),R.drawable.bee,agroassets(sql)));
        while (!cursor.isAfterLast()){
            Long id = cursor.getLong(0);
            String statName = cursor.getString(1);
            sql = "select id,nickname,code,dcode,CAST(dcode as SIGNED) AS casted_column from v_hive where entity_status_id = "+id+" order by casted_column asc, dcode asc";
            agrogrps.add(new AgroassetGroup(statName.toUpperCase(),R.drawable.bee,agroassets(sql)));
            cursor.moveToNext();
        }
        cursor.close();
        pm.close();

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
                data.putLong("prodTypeId",2);
                Intent intent = new Intent(parent.getContext(),HiveEditActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                return false;
            }
        };
    }
}
