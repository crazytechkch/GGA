package co.crazytech.gga.agroasset;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.crazytech.gga.R;

/**
 * Created by eric on 7/28/2016.
 */
public class AgroassetListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<AgroassetGroup> agrogrps;

    public AgroassetListAdapter(Context context, List<AgroassetGroup> agrogrps) {
        this.context = context;
        this.agrogrps = agrogrps;
    }

    @Override
    public int getGroupCount() {
        return agrogrps.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return agrogrps.get(groupPosition).getAgroassets().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return agrogrps.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return agrogrps.get(groupPosition).getAgroassets().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return agrogrps.get(groupPosition).getAgroassets().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.agroasset_group_listitem,null);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        image.setImageResource(agrogrps.get(groupPosition).getImgRes());
        txtTitle.setText(agrogrps.get(groupPosition).getGroupName().toString());
        txtCount.setText(agrogrps.get(groupPosition).getAgroassets().size()+"");
        return convertView;
    }



    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_listitem,null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        Agroasset agroasset = agrogrps.get(groupPosition).getAgroassets().get(childPosition);
        Log.d("Agroasset Adapter",agroasset.toString());
        txtTitle.setText(agroasset.getDcode()+" - "+agroasset.getNickname()+" ("+agroasset.getCode().substring(5)+")");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
