package co.crazytech.gga.hive;

import java.math.BigDecimal;

import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class Hive {
    private int id,entityStatusId,farmId;
    private int geoId;
    private Integer interv_extract,interv_inspect;
    private String nickname,geoCol,geoRow,remark;
    private BigDecimal geoLat,geoLong;

    public Hive(QRResult qrres) {
        geoId = qrres.getGeoId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntityStatusId() {
        return entityStatusId;
    }

    public void setEntityStatusId(int entityStatusId) {
        this.entityStatusId = entityStatusId;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
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
}
