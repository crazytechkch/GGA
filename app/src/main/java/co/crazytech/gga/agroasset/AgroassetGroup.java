package co.crazytech.gga.agroasset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 7/28/2016.
 */
public class AgroassetGroup {
    private String groupName;
    private int imgRes;
    private List<Agroasset> agroassets = new ArrayList<Agroasset>();

    public AgroassetGroup(String groupName,int drawableId, List<Agroasset> agroassets) {
        this.groupName = groupName;
        this.imgRes = drawableId;
        this.agroassets = agroassets;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Agroasset> getAgroassets() {
        return agroassets;
    }

    public void setAgroassets(List<Agroasset> agroassets) {
        this.agroassets = agroassets;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
