package co.crazytech.gga.agroasset.tree;

import android.content.Context;
import android.os.Bundle;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.Agroasset;
import co.crazytech.gga.db.PersistanceManager;
import co.crazytech.gga.farm.Farm;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class Tree extends Agroasset {
    private Integer intervInfuse;


    public Tree(QRResult qrres, Context context) {
        setGeoArea(qrres.getGeoArea());
        setGeoAeid(qrres.getGeoAeid());
        setContext(context);
        setFarm(new Farm(getFarmId(),context));
    }

    public Tree(Context context, Bundle extras, String assetTable) {
        super(context, extras, assetTable);
    }

    @Override
    public String getNickname() {
        return super.getNickname()!=null||!super.getNickname().equals("")?super.getNickname():getContext().getString(R.string.gaharu_tree);
    }

    @Override
    public String dbUpdate(String table) {
        return "update "+table+" set nickname='"+getNickname()+"',"+
                "geo_lat="+getGeoLat()+","+
                "geo_long="+getGeoLong()+","+
                "geo_area='"+getGeoArea()+"',"+
                "geo_aeid='"+getGeoAeid()+"',"+
                "interv_extract="+getIntervExtract()+","+
                "interv_inspect="+getIntervExtract()+","+
                "remark='"+getRemark()+"',"+
                "interv_infuse="+getIntervInfuse()+
                " where id="+getId();
    }

    @Override
    public String dbInsert(String table) {
        return "insert into "+table+" values ("+
                getId()+","+getEntityStatusId()+","+getFarmId()+",'"+getNickname()+"',"+getDate()+","+
                getGeoLat()+","+getGeoLong()+",'"+getGeoArea()+"','"+getGeoAeid()+"',"+
                getIntervExtract()+","+getIntervInspect()+",'"+getRemark()+"',"+getIntervInfuse()+")";
    }

    public Integer getIntervInfuse() {
        return intervInfuse;
    }

    public void setIntervInfuse(Integer intervInfuse) {
        this.intervInfuse = intervInfuse;
    }
}
