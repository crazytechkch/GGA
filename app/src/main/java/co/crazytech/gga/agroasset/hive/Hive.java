package co.crazytech.gga.agroasset.hive;

import android.content.Context;
import android.os.Bundle;

import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.Agroasset;
import co.crazytech.gga.farm.Farm;
import co.crazytech.gga.zbar.QRResult;

/**
 * Created by eric on 7/19/2016.
 */
public class Hive extends Agroasset {


    public Hive(QRResult qrres, Context context) {
//        setGeoArea(qrres.getGeoArea());
//        setGeoAeid(qrres.getGeoAeid());
        setContext(context);
    }

    public Hive(Long id, String nickname, String geoArea, int geoAeid) {
        super(id, nickname,geoArea,geoAeid);
    }

    public Hive(Context context, Bundle extras, String assetTable) {
        super(context, extras, assetTable);
    }
}
