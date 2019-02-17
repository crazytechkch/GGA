package co.crazytech.gga.agroasset;

import android.net.Uri;

/**
 * Created by eric on 12/26/2017.
 */

public class AgroassetContract {
    public static final String CONTENT_AUTHORITY = "co.crazytech.gga";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    static final String PATH_AGROASSETS = "agroassets";

    public static abstract class Agroassets {
        public static final String NAME = "agroassets";
        public static final String ID = "agroassetId";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_AGROASSETS).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_AGROASSETS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_AGROASSETS;
    }
}

