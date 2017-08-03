package co.crazytech.gga.agroasset;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.crazytech.gga.MainActivity;
import co.crazytech.gga.R;
import co.crazytech.gga.db.PersistanceManager;
import ctcommons.SimpleObject;

/**
 * Created by Eric on 3/8/2017.
 */

public class UomSpinnerAdapter extends BaseAdapter {
    private Context context;
    private int uomType;
    private List<SimpleObject> uoms;



    public UomSpinnerAdapter(@NonNull Context context, int uomType) {
        this.context = context;
        this.uomType = uomType;
        uoms = new ArrayList<SimpleObject>();
        String sql = "";
        switch (uomType) {
            case MainActivity.Uom.VOLUME:
                sql = "select id,unit from volume_uom";
                break;
            case MainActivity.Uom.WIEGHT:
                sql = "select id,unit from weight_uom";
                break;
        }
        PersistanceManager pm = new PersistanceManager(context);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try{
            Cursor cursor = db.rawQuery(sql,null);
            Log.d("Uom size",cursor.getCount()+"");
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Long id = cursor.getLong(0);
                String unit = cursor.getString(1);
                uoms.add(new SimpleObject(id,unit));
                cursor.moveToNext();
            }
            cursor.close();
        } catch ( SQLException e){
            Log.w("SQL Error",e.getMessage());
        }
        pm.close();

    }

    @Override
    public int getCount() {
        return uoms.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return uoms.get(position);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_listitem,null);
        }
        TextView title = (TextView)convertView.findViewById(R.id.title);
        title.setText(uoms.get(position).getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_listitem,null);
        }
        TextView title = (TextView)convertView.findViewById(R.id.title);
        title.setText(uoms.get(position).getName());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return uoms.get(position).getId();
    }

    public int getPosition(Long itemId){
        for (int i=0;i<uoms.size();i++){
            if(uoms.get(i).getId()==itemId)return i;
        }
        return 0;
    }

}
