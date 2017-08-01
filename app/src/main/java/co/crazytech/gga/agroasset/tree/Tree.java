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


    public Tree(QRResult qrres, Context context) {
//        setGeoArea(qrres.getGeoArea());
//        setGeoAeid(qrres.getGeoAeid());
        setContext(context);
        setFarm(new Farm(getFarmId(),context));
    }

    public Tree(Context context, Bundle extras, String assetTable) {
        super(context, extras, assetTable);
    }
}
