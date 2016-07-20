package co.crazytech.gga.farm;

import android.content.Context;

import java.math.BigDecimal;

import co.crazytech.gga.R;

/**
 * Created by eric on 7/20/2016.
 */
public class Farm {
    private Context context;
    private Long id;
    private int entityStatusId,geoId;
    private String farmName,address1,address2,address3;
    private String countryCode,regionCode,remark;
    private BigDecimal geoLat,geoLong;
    private Long date;

    public Farm(Long id, Context context) {
        this.id = id;
        this.context = context;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEntityStatusId() {
        return entityStatusId;
    }

    public void setEntityStatusId(int entityStatusId) {
        this.entityStatusId = entityStatusId;
    }

    public int getGeoId() {
        return geoId;
    }

    public void setGeoId(int geoId) {
        this.geoId = geoId;
    }

    public String getFarmName() {
        return farmName!=null?farmName:context.getString(R.string.company_name);
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

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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
