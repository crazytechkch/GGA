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
    private Long id,entityStatusId,farmId,date,geoInfoId,prodTypeId;
    private Integer intervExtract,intervInfuse,intervInspect;
    private String code,dcode,nickname,remark,harvDate;
    private BigDecimal geoLat,geoLong;
    private Context context;
    private Farm farm;
    private EntityStatus entityStatus;

    public static final String TABLE_NAME= "agroasset";

    public Agroasset() {
    }

    public Agroasset(Long id, String nickname, String code, String dcode) {
        this.id = id;
        this.nickname = nickname;
        this.code = code;
        this.dcode = dcode;
     }

    public Agroasset(Context context, Bundle extras, String assetTable) {
        id = extras.getLong("id");
        this.context = context;
        PersistanceManager pm = new PersistanceManager(context);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try{
            Cursor cursor = db.rawQuery("select * from "+assetTable+" where id = ?",new String[]{id+""});
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                id = cursor.getLong(0);
                farmId = cursor.getLong(1);
                entityStatusId = cursor.getLong(2);
                code = cursor.getString(3);
                dcode = cursor.getString(4);
                nickname = cursor.getString(5);
                date = cursor.getLong(6);
                geoInfoId = cursor.getLong(7);
                intervExtract = cursor.getInt(8);
                intervInfuse = cursor.getInt(9);
                intervInspect = cursor.getInt(10);
                prodTypeId = cursor.getLong(11);
                remark = cursor.getString(12);
            } else {
                nickname = extras.getString("nickname");
                prodTypeId = extras.getLong("prodTypeId");
                code = extras.getString("typeCode")+extras.getString("farmCode")+extras.getString("prodCode");
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
        if(farmId!=null&&farmId>0) return new Farm(farmId,context);
        return null;
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

    public Long getGeoInfoId() {
        return geoInfoId;
    }

    public void setGeoAeid(Long geoInfoId) {
        this.geoInfoId = geoInfoId;
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

    public Long getProdTypeId() {
        return prodTypeId;
    }

    public void setProdTypeId(Long prodTypeId) {
        this.prodTypeId = prodTypeId;
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

    public void setGeoInfoId(Long geoInfoId) {
        this.geoInfoId = geoInfoId;
    }

    public Integer getIntervInfuse() {
        return intervInfuse;
    }

    public void setIntervInfuse(Integer intervInfuse) {
        this.intervInfuse = intervInfuse;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getHarvDate() {
        return harvDate;
    }

    public void setHarvDate(String harvDate) {
        this.harvDate = harvDate;
    }

    public String dbUpdate(String table) {
        return "update "+table+" set "+
                "dcode="+checkNullString(getDcode())+","+
                "nickname="+checkNullString(getNickname())+","+
                "entity_status_id="+checkNullLong(getEntityStatusId())+","+
                "geo_info_id="+checkNullLong(getGeoInfoId())+","+
                "interv_extract="+checkNullInt(getIntervExtract())+","+
                "interv_inspect="+checkNullInt(getIntervExtract())+","+
                "remark="+checkNullString(getRemark())+
                " where id="+getId();
    }

    public String dbInsert(String table) {
        return "insert into "+table+" values ("+
                getId()+","+
                getFarmId()+","+
                getEntityStatusId()+","+
                "'"+getCode()+"',"+
                checkNullString(getDcode())+","+
                checkNullString(getNickname())+","+
                checkNullLong(getDate())+","+
                checkNullLong(getGeoInfoId())+","+
                checkNullInt(getIntervExtract())+","+
                checkNullInt(getIntervInfuse())+","+
                checkNullInt(getIntervInspect())+","+
                checkNullLong(getProdTypeId())+","+
                checkNullString(getRemark())+")";
    }

    protected String checkNullString(String value){
        if (value!=null&&value.length()>0) return "'"+value+"'";
        return "NULL";
    }

    protected String checkNullLong(Long value){
        if (value!=null&&value>0) return value+"";
        return "NULL";
    }

    protected String checkNullInt (Integer value){
        if (value!=null&&value>0) return value+"";
        return "NULL";
    }

    @Override
    public String toString() {
        return "id:"+id+",nickname:"+nickname;
    }
}
