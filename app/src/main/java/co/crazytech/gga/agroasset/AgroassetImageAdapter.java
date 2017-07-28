package co.crazytech.gga.agroasset;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.agroasset_image,container,false);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        File imageFile = imageFiles[position];
        try {
            if(!imageFile.exists()||imageFile.isDirectory()) throw new FileNotFoundException();
            Glide.with(context).load(imageFile).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Metadata imgMetadata = ImageMetadataReader.readMetadata(imageFile);
            ExifSubIFDDirectory directory = imgMetadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if(directory!=null){
                Date dateTaken = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                String dateStr = (String)DateFormat.format("dd/MM/yyyy EEE HH:mm:ss",dateTaken);
                TextView textView = (TextView)view.findViewById(R.id.textView);
                textView.setText(dateStr);
            }
        } catch (ImageProcessingException | IOException e) {
//            Log.w("Image Exception",e.getMessage());
        }

        container.addView(view);
        return view;
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
