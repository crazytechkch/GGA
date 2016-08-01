package ctcommons;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.crazytech.ctcommons.R;

/**
 * Created by eric on 8/1/2016.
 */
public class SimpleCustomAdapter extends ArrayAdapter<SimpleObject>{

    private Context context;
    private List<SimpleObject> objs = new ArrayList<SimpleObject>();

    public SimpleCustomAdapter(Context context, List<SimpleObject> objs) {
        super(context,R.layout.simple_listitem);
        this.context = context;
        this.objs = objs;
    }

    @Override
    public int getCount() {
        return objs.size();
    }

    @Override
    public SimpleObject getItem(int position) {
        return objs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objs.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_listitem,null);
        }
        TextView title = (TextView)convertView.findViewById(R.id.title);
        title.setText(getItem(position).getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_listitem,null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(getItem(position).getName());

        return convertView;
    }
}
