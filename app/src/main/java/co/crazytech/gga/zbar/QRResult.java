package co.crazytech.gga.zbar;

/**
 * Created by eric on 7/18/2016.
 */
public class QRResult {
    private int country,region,farm,geoAeid;
    private String type,geoArea;
    private String decodedStr;

    public QRResult(String decodedStr) {
        this.decodedStr = decodedStr;
        if(resType().equals("gga")){
            String[] decodedArr = decodedStr.split("_");
            country = new Integer(decodedArr[1]);
            region = new Integer(decodedArr[2]);
            farm = new Integer(decodedArr[3]);
            type = decodedArr[4];
            geoArea = decodedArr[5];
            geoAeid = new Integer(decodedArr[6]);

        }
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

    public int getGeoAeid() {
        return geoAeid;
    }

    public String getType() {
        return type;
    }

    public String getGeoArea() {
        return geoArea;
    }

    public String resType() {
        if(decodedStr.contains("https://www.facebook.com/GaharuGading"))return "fb";
        else if(decodedStr.startsWith("gga_")) return "gga";
        return "nongga";
    }

    @Override
    public String toString() {
        return "Country:"+country+";Region:"+region+";Farm:"+farm+";Type:"+type+";GeoAreaAeid:"+geoArea+geoAeid;
    }
}
