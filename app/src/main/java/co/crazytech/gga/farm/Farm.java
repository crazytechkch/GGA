package co.crazytech.gga.farm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;

import co.crazytech.gga.R;
import co.crazytech.gga.db.PersistanceManager;

/**
 * Created by eric on 7/20/2016.
 */
public class Farm {
    private Context context;
    private Long id, entityStatusId,geoInfoId,date;
    private String code,farmName,address1,address2,address3;
    private String countryCode,remark;
    private BigDecimal geoLat,geoLong;

    public Farm(Long id, Context context) {
        this.id = id;
        this.context = context;
        PersistanceManager pm = new PersistanceManager(context);
        pm.open();
        SQLiteDatabase db = pm.getDb();
        Cursor cursor = db.rawQuery("select * from farm where id = "+id,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            entityStatusId=cursor.getLong(1);
            code=cursor.getString(2);
            farmName=cursor.getString(3);
            cursor.moveToNext();
        }
        cursor.close();
        pm.close();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityStatusId() {
        return entityStatusId;
    }

    public void setEntityStatusId(Long entityStatusId) {
        this.entityStatusId = entityStatusId;
    }

    public Long getGeoInfoId() {
        return geoInfoId;
    }

    public void setGeoInfoId(Long geoInfoId) {
        this.geoInfoId = geoInfoId;
    }

    public String getFarmName() {
        return farmName!=null?farmName:context.getString(R.string.company_name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
