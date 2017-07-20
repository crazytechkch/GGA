package co.crazytech.gga.zbar;

/**
 * Created by eric on 7/18/2016.
 */
public class QRResult {
    private String typeCode,farmCode,prodCode;
    private String decodedStr;

    public QRResult(String decodedStr) {
        this.decodedStr = decodedStr;
        if(resType().equals("gga")){

            String[] decodedArr = decodedStr.split("_");
            typeCode = decodedStr.substring(2,4);
            farmCode = decodedStr.substring(4,7);
            prodCode = decodedStr.substring(7);

        }
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getFarmCode() {
        return farmCode;
    }

    public void setFarmCode(String farmCode) {
        this.farmCode = farmCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getDecodedStr() {
        return decodedStr;
    }

    public void setDecodedStr(String decodedStr) {
        this.decodedStr = decodedStr;
    }

    public String resType() {
        if(decodedStr.contains("https://www.facebook.com/GaharuGading"))return "fb";
        else if(decodedStr.startsWith("GG")) return "gga";
        return "nongga";
    }

    @Override
    public String toString() {
        return "type:"+typeCode+";farm:"+farmCode+";prod:"+prodCode;
    }
}
