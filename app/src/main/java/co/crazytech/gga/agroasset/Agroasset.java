package co.crazytech.gga.agroasset;

import android.content.Context;

import java.math.BigDecimal;

import co.crazytech.gga.R;
import co.crazytech.gga.farm.Farm;
import co.crazytech.gga.supplement.EntityStatus;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class Agroasset {
    private Long id,entityStatusId,farmId;
    private int geoId;
    private Integer interv_extract,interv_inspect;
    private String nickname,geoCol,geoRow,remark;
    private BigDecimal geoLat,geoLong;
    private Context context;
    private Farm farm;
    private EntityStatus entityStatus;

    public Agroasset() {
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

    public int getGeoId() {
        return geoId;
    }

    public void setGeoId(int geoId) {
        this.geoId = geoId;
    }

    public Integer getInterv_extract() {
        return interv_extract;
    }

    public void setInterv_extract(Integer interv_extract) {
        this.interv_extract = interv_extract;
    }

    public Integer getInterv_inspect() {
        return interv_inspect;
    }

    public void setInterv_inspect(Integer interv_inspect) {
        this.interv_inspect = interv_inspect;
    }

    public String getNickname() {
        return nickname!=null?nickname:context.getString(R.string.hive);
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
}
