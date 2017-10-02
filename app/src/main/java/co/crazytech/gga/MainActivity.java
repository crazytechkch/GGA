package co.crazytech.gga;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import co.crazytech.gga.agroasset.AgroassetEditActivity;
import co.crazytech.gga.agroasset.AgroassetListActivity;
import co.crazytech.gga.agroasset.hive.Hive;
import co.crazytech.gga.agroasset.hive.HiveEditActivity;
import co.crazytech.gga.agroasset.hive.HiveListActivity;
import co.crazytech.gga.agroasset.tree.TreeEditActivity;
import co.crazytech.gga.agroasset.tree.TreeListActivity;
import co.crazytech.gga.db.PersistanceManager;
import co.crazytech.gga.zbar.BarcodeScanner;
import co.crazytech.gga.zbar.QRResult;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int QRRES = 0;
    public static final String STORAGE_DIR = Environment.getExternalStorageDirectory()+"/GAHARU-M/";
    public static final int PERM_READ_SD = 0;

    public static class Request {
        public static final int REQ_REC_NEW = 0;
        public static final int REQ_REC_EDIT = 1;
    }

    public static class Uom {
        public static final int WIEGHT = 0;
        public static final int VOLUME = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BarcodeScanner.class);
                startActivityForResult(intent,QRRES);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton btnHive = (ImageButton)findViewById(R.id.buttonHive);
        btnHive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HiveListActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnTree = (ImageButton)findViewById(R.id.buttonTree);
        btnTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TreeListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = null;

        switch (id) {
            case R.id.nav_tree:
                fragmentClass = AgroassetListActivity.class;
                break;
            case R.id.nav_hive:
                fragmentClass = AgroassetListActivity.class;
                break;
            case R.id.nav_qr:
                fragmentClass = BarcodeScanner.class;
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }
        /*
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e){

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==QRRES && resultCode== Activity.RESULT_OK){
            String qrresStr = data.getStringExtra("scanres");
            Intent intent = new Intent();
            QRResult qrres = new QRResult(qrresStr);
            if (qrres.resType().equals("fb")){
                intent = newFacebookIntent(this.getPackageManager(),qrresStr);
                startActivity(intent);
            }
            else if(qrres.resType().equals("gga")){
                qrres = new QRResult(qrresStr);
                Bundle extras = new Bundle();
                if(qrres.getTypeCode().equals("BA")){
                    intent = new Intent(this, HiveEditActivity.class);
                    putExtraFromDb(extras,qrres,"v_hive");
                    extras.putLong("prodTypeId",2);
                }
                if(qrres.getTypeCode().equals("AA")) {
                    intent = new Intent(this, TreeEditActivity.class);
                    putExtraFromDb(extras,qrres,"v_tree");
                    extras.putLong("prodTypeId",1);
                }
                else {
                    intent = new Intent(this, AgroassetEditActivity.class);
                    putExtraFromDb(extras,qrres,"agroasset");
                }
                extras.putString("typeCode",qrres.getTypeCode());
                extras.putString("farmCode",qrres.getFarmCode());
                extras.putString("prodCode",qrres.getProdCode());
                intent.putExtras(extras);
                startActivity(intent);
            }
            else Toast.makeText(this,"Non Gaharu Gading code\n scanned result - "+qrresStr,Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void putExtraFromDb(Bundle extras, QRResult qrres,String table){
        PersistanceManager pm = new PersistanceManager(this);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        if(pm.rowExists(table,"code = '"+qrres.getCode()+"'")){
            Cursor cursor = db.rawQuery("select id,nickname,dcode from "+table+" where code = '"+qrres.getCode()+"' limit 1",null);
            cursor.moveToFirst();
            extras.putLong("id",cursor.getLong(0));
            extras.putString("nickname",cursor.getString(1));
            extras.putString("dcode",cursor.getString(0));
            cursor.close();
        } else {
            extras.putLong("id",0);
        }
        pm.close();

    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
