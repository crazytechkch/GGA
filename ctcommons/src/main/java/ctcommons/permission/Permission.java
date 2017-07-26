package ctcommons.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by eric on 7/26/2017.
 */

public class Permission {

    public static boolean isPermissionGranted(Activity activity, String permission, int permCode) {
        if (ContextCompat.checkSelfPermission(activity, permission)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){

            }
            else {
                ActivityCompat.requestPermissions(activity,new String[]{permission},permCode);
            }
            return false;
        }
        return true;
    }
}
