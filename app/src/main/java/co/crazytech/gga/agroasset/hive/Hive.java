package co.crazytech.gga.agroasset.hive;

import android.content.Context;

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
        setFarm(new Farm(getFarmId(),context));
    }


 }
