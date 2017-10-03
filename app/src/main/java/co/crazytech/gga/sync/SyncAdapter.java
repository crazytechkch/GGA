package co.crazytech.gga.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import co.crazytech.gga.db.DbHelper;
import retrofit2.Retrofit;

/**
 * Created by eric on 9/29/2017.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private SharedPreferences prefs;
    private SyncService syncService;
    private ContentResolver contentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        init(context);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        init(context);
    }

    private void init(Context context){
        contentResolver = context.getContentResolver();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("http://swopt.gaharu.co:8080/").build();
        syncService = retro.create(SyncService.class);


    }

    @Override
    public void onPerformSync(Account account, Bundle extras,
              String authority, ContentProviderClient provider, SyncResult syncResult) {

    }
}
