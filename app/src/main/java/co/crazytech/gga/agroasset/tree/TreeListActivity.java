package co.crazytech.gga.agroasset.tree;

import android.content.Intent;
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
        agrogrps.add(new AgroassetGroup(getString(R.string.extraction_pending), R.drawable.sickle,new ArrayList<Agroasset>()));
        agrogrps.add(new AgroassetGroup(getString(R.string.inspection_pending), R.drawable.detective,new ArrayList<Agroasset>()));
        agrogrps.add(new AgroassetGroup(getString(R.string.infusion_pending), R.drawable.syringe,new ArrayList<Agroasset>()));
        sql = "select id,nickname,geo_info_id from v_tree order by date desc";
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
