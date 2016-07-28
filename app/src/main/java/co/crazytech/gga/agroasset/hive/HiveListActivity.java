package co.crazytech.gga.agroasset.hive;

import android.os.Bundle;

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
public class HiveListActivity extends AgroassetListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.hive);
        List<AgroassetGroup> agrogrps = new ArrayList<AgroassetGroup>();
        agrogrps.add(new AgroassetGroup(getString(R.string.extraction_pending), R.drawable.sickle,new ArrayList<Agroasset>()));
        agrogrps.add(new AgroassetGroup(getString(R.string.inspection_pending), R.drawable.detective,new ArrayList<Agroasset>()));
        agrogrps.add(new AgroassetGroup(getString(R.string.view_all), R.drawable.bee,new ArrayList<Agroasset>()));
        setListAdapter(new AgroassetListAdapter(this,agrogrps));
        super.onCreate(savedInstanceState);
    }
}
