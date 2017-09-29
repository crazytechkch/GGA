package co.crazytech.gga.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by eric on 9/29/2017.
 */

public class SyncService extends Service {

    private static SyncAdapter syncAdapter = null;
    private static final Object SYNC_ADAPTER_LOCK = new Object();

    @Override
    public void onCreate() {
        synchronized (SYNC_ADAPTER_LOCK) {
            if (syncAdapter == null) syncAdapter = new SyncAdapter(getApplicationContext(),true);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
