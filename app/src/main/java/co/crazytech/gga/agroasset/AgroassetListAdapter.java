package co.crazytech.gga.agroasset;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.agroasset_listitem,null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,0.9f));
        Agroasset agroasset = agrogrps.get(groupPosition).getAgroassets().get(childPosition);
        txtTitle.setText(agroasset.getDcode()+" - "+agroasset.getNickname()+" ("+agroasset.getCode().substring(5)+")");
        ImageButton btnDelete = (ImageButton) convertView.findViewById(R.id.fabDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Delete this record?", new Runnable() {
                    @Override
                    public void run() {
                        agrogrps.get(groupPosition).getAgroassets().remove(childPosition);
                        notifyDataSetChanged();
                    }
                });
            }
        });

        LinearLayout linlay = (LinearLayout) convertView.findViewById(R.id.linlay);
        linlay.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void showAlertDialog(String message, final Runnable runnable) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setCancelable(true)
                .setMessage(message)
                .setPositiveButton(context.getString(android.R.string.ok),new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        runnable.run();
                    }
                })
                .setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
