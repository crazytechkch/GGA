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
        setGeoId(qrres.getGeoId());
        setContext(context);
    }

    public Hive(Long id, int geoId, String nickname, String geoCol, String geoRow) {
        super(id, geoId, nickname,geoCol,geoRow);
    }

    public Hive(Context context, Bundle extras, String assetTable) {
        super(context, extras, assetTable);
    }
}
