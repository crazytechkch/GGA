package co.crazytech.gga.agroasset;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import co.crazytech.gga.R;

/**
 * Created by Eric on 7/24/2017.
 */

public class AgroassetImageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private File[] imageFiles;

    public AgroassetImageAdapter(Context context, File[] imageFiles) {
        this.imageFiles = imageFiles;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.agroasset_image,container,false);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        FloatingActionButton fabDelete = (FloatingActionButton)view.findViewById(R.id.fabDelete);
        final File imageFile = imageFiles[position];
        try {
            if(!imageFile.exists()||imageFile.isDirectory()) throw new FileNotFoundException();
            Glide.with(context).load(imageFile).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final View v = view;

            fabDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog("Delete this image?", imageFile);
                }
            });

            Metadata imgMetadata = ImageMetadataReader.readMetadata(imageFile);
            ExifSubIFDDirectory directory = imgMetadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            String dateStr = "";
            String remark = "";
            String imgIdx = (position+1)+" of "+imageFiles.length;
            if(directory!=null){
                Date dateTaken = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED);
                if(dateTaken!=null) {
                    dateTaken.setTime(dateTaken.getTime()-43200000);
                    dateStr = (String)DateFormat.format("dd/MM/yyyy EEE HH:mm:ss",dateTaken);
                }
            }
            TextView textView = (TextView)view.findViewById(R.id.textView);
            textView.setText(
                    ((dateStr!=null&&dateStr.length()>0)?dateStr:"")+
                    ((remark!=null&&remark.length()>0)?"\n":"")+
                    remark+"\n"+imgIdx);
        } catch (ImageProcessingException | IOException e) {
//            Log.w("Image Exception",e.getMessage());
        }

        container.addView(view);
        return view;
    }

    private void showAlertDialog(String message,final File imgFile) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setCancelable(true)
                .setMessage(message)
                .setPositiveButton(context.getString(android.R.string.ok),new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgFile.delete();
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

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (View) o;
    }

    @Override
    public int getCount() {
        return imageFiles.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
