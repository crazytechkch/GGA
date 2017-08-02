package co.crazytech.gga.agroasset.extract;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import co.crazytech.gga.db.PersistanceManager;

/**
 * Created by eric on 8/1/2017.
 */

public class AgroassetExtract {
    private Long id,agroassetId,volUomId,weightUomId;
    private Long extractType,prodTypeId;
    private Integer podCount;
    private Double weight,volume;
    private String date,remark,weightUnit,volumeUnit;

    public AgroassetExtract(Context context, Long id, String sqlView) {
        this.id = id;
        populate(context,sqlView);
    }

    private void populate(Context context, String sqlView){
        PersistanceManager pm = new PersistanceManager(context);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        String sql = "select ae.*,w.unit,v.unit from "+sqlView+" ae  " +
                "left join weight_uom w on ae.weight_uom_id = w.id " +
                "left join volume_uom v on ae.volume_uom_id = v.id " +
                "where ae.id = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{(id!=null?id:0)+""});
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            id = cursor.getLong(0);
            agroassetId = cursor.getLong(1);
            volUomId = cursor.getLong(2);
            weightUomId = cursor.getLong(3);
            extractType = cursor.getLong(4);
            date = cursor.getString(5);
            podCount = cursor.getInt(6);
            weight = cursor.getDouble(7);
            volume = cursor.getDouble(8);
            remark = cursor.getString(9);
            prodTypeId = cursor.getLong(10);
            weightUnit = cursor.getString(11);
            volumeUnit = cursor.getString(12);
        }
        cursor.close();
        pm.close();
    }

    public boolean dbUpdate(Context context){
        boolean success;
        PersistanceManager pm = new PersistanceManager(context);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try{
            ContentValues cv = new ContentValues();
            cv.put("agroasset_id",agroassetId);
            cv.put("volume_uom_id",volUomId);
            cv.put("weight_uom_id",weightUomId);
            cv.put("extract_type",extractType);
            cv.put("date",date);
            cv.put("pod_count",podCount);
            cv.put("weight",weight);
            cv.put("volume",volume);
            cv.put("remark",remark);
            db.update("agroasset_extract",cv,"id=?",new String[]{id+""});
            success=true;
        } catch (SQLException e){
            success=false;
            Log.w("SQL Update Error",e.getMessage());
        }
        pm.close();
        return success;
    }

    public boolean dbInsert(Context context){
        boolean success;
        PersistanceManager pm = new PersistanceManager(context);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        try{
            ContentValues cv = new ContentValues();
            cv.put("agroasset_id",agroassetId);
            cv.put("volume_uom_id",volUomId);
            cv.put("weight_uom_id",weightUomId);
            cv.put("extract_type",extractType);
            cv.put("date",date);
            cv.put("pod_count",podCount);
            cv.put("weight",weight);
            cv.put("volume",volume);
            cv.put("remark",remark);
            db.insert("agroasset_extract","NULL",cv);
            success = true;
        } catch (SQLException e){
            success=false;
            Log.w("SQL Update Error",e.getMessage());
        }
        pm.close();
        return success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgroassetId() {
        return agroassetId;
    }

    public void setAgroassetId(Long agroassetId) {
        this.agroassetId = agroassetId;
    }

    public Long getVolUomId() {
        return volUomId;
    }

    public void setVolUomId(Long volUomId) {
        this.volUomId = volUomId;
    }

    public Long getWeightUomId() {
        return weightUomId;
    }

    public void setWeightUomId(Long weightUomId) {
        this.weightUomId = weightUomId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getExtractType() {
        return extractType;
    }

    public void setExtractType(Long extractType) {
        this.extractType = extractType;
    }

    public Integer getPodCount() {
        return podCount;
    }

    public void setPodCount(Integer podCount) {
        this.podCount = podCount;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getProdTypeId() {
        return prodTypeId;
    }

    public void setProdTypeId(Long prodTypeId) {
        this.prodTypeId = prodTypeId;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }
}
