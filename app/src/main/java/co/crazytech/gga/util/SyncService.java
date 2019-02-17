package co.crazytech.gga.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by eric on 12/21/2017.
 */

public class SyncService extends Service {
    private static SyncAdapter syncAdapter = null;
    private static final Object syncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (syncAdapterLock) {
            if (syncAdapter==null) syncAdapter = new SyncAdapter(getApplicationContext(),true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
