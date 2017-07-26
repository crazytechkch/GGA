package co.crazytech.gga.agroasset;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.File;

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
        Glide.with(context).load(imageFiles[position]).into(imageView);

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
        container.removeView((LinearLayout) object);
    }
}
