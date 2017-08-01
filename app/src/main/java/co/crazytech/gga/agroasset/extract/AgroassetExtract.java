package co.crazytech.gga.agroasset.extract;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import co.crazytech.gga.db.PersistanceManager;

/**
 * Created by eric on 8/1/2017.
 */

public class AgroassetExtract {
    private Long id,agroassetId,volUomId,weightUomId;
    private Long date,extractType,prodTypeId;
    private Integer podCount;
    private Double weight,volume;
    private String remark;

    public AgroassetExtract(Context context, Long id, String sqlView) {
        this.id = id;
        populate(context,sqlView);
    }

    private void populate(Context context, String sqlView){
        PersistanceManager pm = new PersistanceManager(context);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        Cursor cursor = db.rawQuery("select * from "+sqlView+" where id="+id,null);
        cursor.moveToFirst();
        id = cursor.getLong(0);
        agroassetId = cursor.getLong(1);
        volUomId = cursor.getLong(2);
        weightUomId = cursor.getLong(3);
        extractType = cursor.getLong(4);
        date = cursor.getLong(5);
        podCount = cursor.getInt(6);
        weight = cursor.getDouble(7);
        volume = cursor.getDouble(8);
        remark = cursor.getString(9);
        prodTypeId = cursor.getLong(10);
        cursor.close();
        pm.close();
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
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
}
