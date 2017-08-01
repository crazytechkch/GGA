package co.crazytech.gga.agroasset.extract;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.crazytech.gga.R;
import co.crazytech.gga.db.PersistanceManager;

/**
 * Created by eric on 8/1/2017.
 */

public class AgroassetExtractActivity extends Activity {
    private List<AgroassetExtract> extracts;
    private ListView lv;
    private Long agroassetId;
    private String sqlView,nickname,dcode,code;
    private static final int REQ_REC_EDIT = 0;
    private static final int REQ_REC_NEW = 1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset_extract);
        populateExtracts();
        initView();
    }

    protected void initView(){
        TextView tvDcode,tvNickname;
        tvNickname = (TextView)findViewById(R.id.textViewNickname);
        tvNickname.setText(getDcode()+". "+getNickname()+" ("+getCode().substring(5)+")");

        lv = (ListView)findViewById(R.id.lvExtracts);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long extractId = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(view.getContext(),AgroassetExtractEditActivity.class);
                intent.putExtra("id",extractId);
                intent.putExtra("sqlView",getSqlView());
                intent.putExtra("dcode",getDcode());
                intent.putExtra("nickname",getNickname());
                intent.putExtra("code",getCode());
                startActivityForResult(intent,REQ_REC_EDIT);

            }
        });
        lv.setAdapter(new AgroassetExtractListAdapter(this,extracts));

    }

    private void populateExtracts(){
        extracts = new ArrayList<AgroassetExtract>();
        PersistanceManager pm = new PersistanceManager(this);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        Cursor cursor = db.rawQuery("select id from "+sqlView+" where agroasset_id="+agroassetId,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            extracts.add(new AgroassetExtract(this,cursor.getLong(0),sqlView));
            cursor.moveToNext();
        }
        cursor.close();
        pm.close();
    }

    public Long getAgroassetId() {
        return agroassetId;
    }

    public void setAgroassetId(Long agroassetId) {
        this.agroassetId = agroassetId;
    }

    public String getSqlView() {
        return sqlView;
    }

    public void setSqlView(String sqlView) {
        this.sqlView = sqlView;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
