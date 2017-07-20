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
    private Integer intervExtract,intervInspect,geoAeid;
    private String nickname,geoArea,remark,type;
    private BigDecimal geoLat,geoLong;
    private Context context;
    private Farm farm;
    private EntityStatus entityStatus;

    public Agroasset() {
    }

    public Agroasset(Long id, String nickname, String geoArea, int geoAeid) {
        this.id = id;
        this.nickname = nickname;
        this.geoArea = geoArea;
        this.geoAeid = geoAeid;
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
                geoLat = new BigDecimal(cursor.getDouble(5));
                geoLong = new BigDecimal(cursor.getDouble(6));
                geoArea = cursor.getString(7);
                geoAeid = cursor.getInt(8);
                intervExtract = cursor.getInt(9);
                intervInspect = cursor.getInt(10);
                remark = cursor.getString(11);
                type = extras.getString("type");
            } else {
                geoArea = extras.getString("geoArea");
                geoAeid = extras.getInt("geoAeid");
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
//        geoArea = qrres.getGeoArea();
//        geoAeid = qrres.getGeoAeid();
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

    public String getGeoArea() {
        return geoArea;
    }

    public void setGeoArea(String geoArea) {
        this.geoArea = geoArea;
    }

    public Integer getGeoAeid() {
        return geoAeid;
    }

    public void setGeoAeid(Integer geoAeid) {
        this.geoAeid = geoAeid;
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
                "geo_area='"+getGeoArea()+"',"+
                "geo_aeid='"+getGeoAeid()+"',"+
                "interv_extract="+getIntervExtract()+","+
                "interv_inspect="+getIntervExtract()+","+
                "remark='"+getRemark()+"'"+
                " where id="+getId();
    }

    public String dbInsert(String table) {
        return "insert into "+table+" values ("+
                getId()+","+getEntityStatusId()+","+getFarmId()+",'"+getNickname()+"',"+getDate()+","+
                getGeoLat()+","+getGeoLong()+",'"+getGeoArea()+"','"+getGeoAeid()+"',"+
                getIntervExtract()+","+getIntervInspect()+",'"+getRemark()+"')";
    }

    @Override
    public String toString() {
        return "id:"+id+",nickname:"+nickname+",geoArea:"+geoArea+",geoRow:"+geoAeid;
    }
}
