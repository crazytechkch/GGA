package co.crazytech.gga.zbar;

/**
 * Created by eric on 7/18/2016.
 */
public class QRResult {
    private int country,region,farm,geoId;
    private String type;

    public QRResult(String decodedStr) {
        String[] decodedArr = decodedStr.split("_");
        country = new Integer(decodedArr[0]);
        region = new Integer(decodedArr[1]);
        farm = new Integer(decodedArr[2]);
        String typeId = decodedArr[3];
        geoId = new Integer(typeId.substring(1,typeId.length()));
        type = typeId.substring(0,1).trim();
    }

    public int getCountry() {
        return country;
    }

    public int getRegion() {
        return region;
    }

    public int getFarm() {
        return farm;
    }

    public int getGeoId() {
        return geoId;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Country:"+country+";Region:"+region+";Farm:"+farm+";Type:"+type+";GeoID:"+geoId;
    }
}
