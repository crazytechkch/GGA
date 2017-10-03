package co.crazytech.gga.agroasset;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.crazytech.gga.MainActivity;
import co.crazytech.gga.R;
import co.crazytech.gga.camera.CameraActivity;
import co.crazytech.gga.db.PersistanceManager;
import ctcommons.SimpleCustomAdapter;
import ctcommons.SimpleObject;
import ctcommons.permission.Permission;

/**
 * Created by eric on 7/19/2016.
 */
public class AgroassetEditActivity extends AppCompatActivity{
    private EditText etId,etNickname,etRemark;
    private FloatingActionButton btnInspectRec,btnExtractRec,btnInfuseRec;
    private ViewPager imagePager;
    private Button btnDone;
    private Spinner spnFarm,spnStatus;
    private Agroasset agroasset;
    private String dataDir;
    public static final int REQ_PICK_IMAGE = 0;
    public static final int REQ_CAPTURE_IMAGE = 1;
    public static final int REQ_EXTRACT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset_edit);

        initAgroasset();

        etId = (EditText)findViewById(R.id.editTextDcode);
        etId.setText(agroasset.getDcode());
        etNickname = (EditText)findViewById(R.id.editTextNickname);
        etNickname.setText(agroasset.getNickname());
        etRemark= (EditText)findViewById(R.id.editTextRemark);
        etRemark.setText(agroasset.getRemark());

        TextView tvId = (TextView)findViewById(R.id.textViewId);
        tvId.setText(agroasset.getCode()!=null?agroasset.getCode().substring(5):"NOCODE");

        spnFarm = (Spinner)findViewById(R.id.spinnerFarm);
        spnFarm.setAdapter(farmAdapter());
        Long farmId = getAgroasset().getFarmId();
        spnFarm.setSelection(farmId!=null?(Integer.valueOf(farmId+"")-1):0);

        spnStatus = (Spinner)findViewById(R.id.spinnerStatus);
        spnStatus.setAdapter(statusAdapter());
        Long entityStatusId = getAgroasset().getEntityStatusId();
        spnStatus.setSelection(entityStatusId!=null?(Integer.valueOf(entityStatusId+"")-1):0);

        imagePager = (ViewPager)findViewById(R.id.imagepager);
        initImageAdapter();


        btnInspectRec = (FloatingActionButton) findViewById(R.id.fabInspect);
        if (!isRowExists()) btnInspectRec.setEnabled(false);
        btnExtractRec = (FloatingActionButton)findViewById(R.id.fabExtract);
        btnExtractRec.setOnClickListener(btnExtractListener());
        if (!isRowExists()) btnExtractRec.setEnabled(false);
        btnInfuseRec = (FloatingActionButton)findViewById(R.id.fabInfuse);
        if (!isRowExists()) btnInfuseRec.setEnabled(false);

        btnDone = (Button)findViewById(R.id.buttonDone);
        initImageButtons();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected void initAgroasset(){
        Bundle extras = getIntent().getExtras();
        setAgroasset(new Agroasset(this,extras,"agroasset"));
    }


    protected View.OnClickListener btnExtractListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private SimpleCustomAdapter farmAdapter() {
        List<SimpleObject> simObjs = new ArrayList<SimpleObject>();
        PersistanceManager pm = new PersistanceManager(this);
        String sql = "select id,name from farm order by name asc";
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try {
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                simObjs.add(new SimpleObject(cursor.getLong(0),cursor.getString(1)));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("SQL Exception",e.getMessage());
        } finally {
            pm.close();
        }
        SimpleCustomAdapter adapter = new SimpleCustomAdapter(this, simObjs);
        return adapter;
    }
    private SimpleCustomAdapter statusAdapter() {
        List<SimpleObject> simObjs = new ArrayList<SimpleObject>();
        PersistanceManager pm = new PersistanceManager(this);
        String sql = "select id,status_name from entity_status";
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try {
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                simObjs.add(new SimpleObject(cursor.getLong(0),cursor.getString(1)));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("SQL Exception",e.getMessage());
        } finally {
            pm.close();
        }
        SimpleCustomAdapter adapter = new SimpleCustomAdapter(this, simObjs);
        return adapter;
    }

    public Long getNewId(SQLiteDatabase db, String assetTable) {
        Cursor cursor = db.rawQuery("select max(id) from "+assetTable,null);
        cursor.moveToFirst();
        Long maxId = cursor.getLong(0);
        cursor.close();
        return maxId+1;
    }

    public Agroasset getAgroasset() {
        return agroasset;
    }

    public ImageButton getBtnInspectRec() {
        return btnInspectRec;
    }

    public ImageButton getBtnExtractRec() {
        return btnExtractRec;
    }

    public ImageButton getBtnInfuseRec() {
        return btnInfuseRec;
    }

    public Button getBtnDone() {
        return btnDone;
    }

    public void setAgroasset(Agroasset agroasset) {
        this.agroasset = agroasset;
    }

    public View.OnClickListener doneListener(final String table){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDone(table);
            }
        };
    }

    public boolean isRowExists() {
        PersistanceManager pm = new PersistanceManager(this);
        if(agroasset.getProdTypeId()==2)return pm.pmRowExists("v_hive","id = "+agroasset.getId());
        if(agroasset.getProdTypeId()==1)return pm.pmRowExists("v_tree","id = "+agroasset.getId());
        return false;
    }

    private void onDone(String assetTable) {
        PersistanceManager pm = new PersistanceManager(this);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try {
            getAgroasset().setEntityStatusId(spnStatus.getSelectedItemId());
            getAgroasset().setFarmId(spnFarm.getSelectedItemId());
            getAgroasset().setNickname(etNickname.getText().toString());
            getAgroasset().setRemark(etRemark.getText().toString());
            getAgroasset().setDcode(etId.getText().toString());
            if (isRowExists()) {
                db.execSQL(getAgroasset().dbUpdate(assetTable));
            } else {
                getAgroasset().setId(getNewId(db,assetTable));
                db.execSQL(getAgroasset().dbInsert(assetTable));
            }
            Toast.makeText(this,getString(R.string.succes),Toast.LENGTH_LONG).show();
        } catch (SQLException e){
            Log.e("Agroasset SQL",e.getMessage());
            Toast.makeText(this,getString(R.string.error),Toast.LENGTH_LONG).show();
        }
        pm.close();
        finish();
    }

    private void initImageAdapter(){
        List<File> images = new ArrayList<File>();
        dataDir = "";
        if(Permission.isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE,1))dataDir = MainActivity.STORAGE_DIR+"agroasset/pictures/"+agroasset.getCode();
        File imageDir = new File(dataDir);
        if(!imageDir.exists()&&Permission.isPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,2))imageDir.mkdirs();
        for (File file:imageDir.listFiles()) {
            images.add(file);
        }
        imagePager.setAdapter(new AgroassetImageAdapter(this, images));

    }

    private void initImageButtons() {
        FloatingActionButton fabCamera,fabGallery;
        fabGallery = (FloatingActionButton)findViewById(R.id.fabGallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_PICK_IMAGE);
            }
        });
        fabCamera = (FloatingActionButton)findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CameraActivity.class);
                startActivityForResult(intent,REQ_CAPTURE_IMAGE);
            }
        });
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public EditText getEtId() {
        return etId;
    }

    public EditText getEtNickname() {
        return etNickname;
    }

    public EditText getEtRemark() {
        return etRemark;
    }

    public Spinner getSpnFarm() {
        return spnFarm;
    }

    public Spinner getSpnStatus() {
        return spnStatus;
    }

    public static class TYPE {
        public static final int TREE = 1;
        public static final int HIVE = 2;
    }

    private void onGalleryResult(Intent data) {
        Uri uri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            double ratio = 1;
            if(bitmap.getWidth()>=bitmap.getHeight())ratio = bitmap.getWidth()/1280;
            else ratio = bitmap.getWidth()/720;
            Bitmap resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()/ratio),(int)(bitmap.getHeight()/ratio),true);
            String filePath = dataDir+"/"+agroasset.getCode()+"_"+timeStamp;
            FileOutputStream out = new FileOutputStream(filePath);
            resized.compress(Bitmap.CompressFormat.JPEG,90,out);
            out.close();
            String inPath = getRealPathFromURI(this,uri);
            ExifInterface exifIn = new ExifInterface(inPath);
            ExifInterface exifOut = new ExifInterface(filePath);
            exifOut.setAttribute(ExifInterface.TAG_DATETIME,exifIn.getAttribute(ExifInterface.TAG_DATETIME));
            exifOut.setAttribute(ExifInterface.TAG_DATETIME_DIGITIZED,exifIn.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED));
            exifOut.saveAttributes();
            deleteCache(this);
            initImageAdapter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        deleteCache(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case REQ_PICK_IMAGE: onGalleryResult(data);
            }
        }
    }
}
