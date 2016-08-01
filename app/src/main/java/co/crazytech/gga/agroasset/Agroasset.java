package co.crazytech.gga.agroasset;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.math.BigDecimal;

import co.crazytech.gga.R;
import co.crazytech.gga.db.PersistanceManager;
import co.crazytech.gga.farm.Farm;
import co.crazytech.gga.supplement.EntityStatus;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class Agroasset {
    private Long id,entityStatusId,farmId,date;
    private int geoId;
    private Integer intervExtract,intervInspect;
    private String nickname,geoCol,geoRow,remark,type;
    private BigDecimal geoLat,geoLong;
    private Context context;
    private Farm farm;
    private EntityStatus entityStatus;

    public Agroasset() {
    }

    public Agroasset(Long id, int geoId, String nickname, String geoCol, String geoRow) {
        this.id = id;
        this.geoId = geoId;
        this.nickname = nickname;
        this.geoCol = geoCol;
        this.geoRow = geoRow;
    }

    public Agroasset(Context context, Bundle extras, String assetTable) {
        id = extras.getLong("id");
        PersistanceManager pm = new PersistanceManager(context);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try{
            Cursor cursor = db.rawQuery("select * from "+assetTable+" where id = ?",new String[]{id+""});
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                id = cursor.getLong(0);
                entityStatusId = cursor.getLong(1);
                farmId = cursor.getLong(2);
                nickname = cursor.getString(3);
                date = cursor.getLong(4);
                geoId = cursor.getInt(5);
                geoLat = new BigDecimal(cursor.getDouble(6));
                geoLong = new BigDecimal(cursor.getDouble(7));
                geoCol = cursor.getString(8);
                geoRow = cursor.getString(9);
                intervExtract = cursor.getInt(10);
                intervInspect = cursor.getInt(11);
                remark = cursor.getString(12);
                type = extras.getString("type");
            } else {
                geoId = extras.getInt("geoId");
                nickname = extras.getString("nickname");
                type = extras.getString("type");
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("Agroasset SQL",e.getMessage());
        }
        pm.close();
    }



    public Agroasset(QRResult qrres, Context context) {
        geoId = qrres.getGeoId();
        this.context = context;
        farm = new Farm(farmId,context);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }

    public Long getEntityStatusId() {
        return entityStatusId;
    }

    public void setEntityStatusId(Long entityStatusId) {
        this.entityStatusId = entityStatusId;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getGeoId() {
        return geoId;
    }

    public void setGeoId(int geoId) {
        this.geoId = geoId;
    }

    public Integer getIntervExtract() {
        return intervExtract;
    }

    public void setIntervExtract(Integer interv_extract) {
        this.intervExtract = interv_extract;
    }

    public Integer getIntervInspect() {
        return intervInspect;
    }

    public void setIntervInspect(Integer interv_inspect) {
        this.intervInspect = interv_inspect;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGeoCol() {
        return geoCol;
    }

    public void setGeoCol(String geoCol) {
        this.geoCol = geoCol;
    }

    public String getGeoRow() {
        return geoRow;
    }

    public void setGeoRow(String geoRow) {
        this.geoRow = geoRow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(BigDecimal geoLat) {
        this.geoLat = geoLat;
    }

    public BigDecimal getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(BigDecimal geoLong) {
        this.geoLong = geoLong;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String dbUpdate(String table) {
        return "update "+table+" set nickname='"+getNickname()+"',"+
                "geo_lat="+getGeoLat()+","+
                "geo_long="+getGeoLong()+","+
                "geo_col='"+getGeoCol()+"',"+
                "geo_row='"+getGeoRow()+"',"+
                "interv_extract="+getIntervExtract()+","+
                "interv_inspect="+getIntervExtract()+","+
                "remark='"+getRemark()+"'"+
                " where id="+getId();
    }

    public String dbInsert(String table) {
        return "insert into "+table+" values ("+
                getId()+","+getEntityStatusId()+","+getFarmId()+",'"+getNickname()+"',"+getDate()+","+
                getGeoId()+","+getGeoLat()+","+getGeoLong()+",'"+getGeoCol()+"','"+getGeoRow()+"',"+
                getIntervExtract()+","+getIntervInspect()+",'"+getRemark()+"')";
    }

    @Override
    public String toString() {
        return "id:"+id+",geoId:"+geoId+",nickname:"+nickname+",geoCol:"+geoCol+",geoRow:"+geoRow;
    }
}
