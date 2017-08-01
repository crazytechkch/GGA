package co.crazytech.gga.agroasset.extract;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import co.crazytech.gga.R;

/**
 * Created by eric on 8/1/2017.
 */

public class AgroassetExtractListAdapter extends BaseAdapter {
    private Context context;
    private List<AgroassetExtract> extracts;

    public AgroassetExtractListAdapter(Context context, List<AgroassetExtract> extracts) {
        this.context = context;
        this.extracts = extracts;
    }

    @Override
    public int getCount() {
        return extracts.size();
    }

    @Override
    public Object getItem(int position) {
        return extracts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return extracts.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.agroasset_listitem,null);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.title);
        AgroassetExtract extract = extracts.get(position);
        new SimpleDateFormat();
        String title = "";
        String dateStr = "";
        if(extract.getDate()!=null)dateStr = new SimpleDateFormat("yyyy-MM-dd EEE HH:mm:ss").format(new Date(extract.getDate()));
        String vol = "v:"+(extract.getVolume()!=null?String.format("%.2d",extract.getVolume()):"/");
        String weight = "w:"+(extract.getWeight()!=null?String.format("%.2d",extract.getWeight()):"/");
        if(extract.getProdTypeId()==1) title = dateStr+" "+vol+" "+weight;
        else if(extract.getProdTypeId()==2) {
            String pod = "pod:"+(extract.getPodCount()!=null?extract.getPodCount():"/");
            title = dateStr+" "+pod+" "+vol+" "+weight;
        }
        tvTitle.setText(title);
        ImageButton btnDelete = (ImageButton) convertView.findViewById(R.id.fabDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Delete this record?", new Runnable() {
                    @Override
                    public void run() {
                        extracts.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        return convertView;
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
