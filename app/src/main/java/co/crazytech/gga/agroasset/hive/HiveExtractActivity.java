package co.crazytech.gga.agroasset.hive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import co.crazytech.gga.agroasset.extract.AgroassetExtract;
import co.crazytech.gga.agroasset.extract.AgroassetExtractActivity;

/**
 * Created by eric on 8/1/2017.
 */

public class HiveExtractActivity extends AgroassetExtractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        setAgroassetId(extras.getLong("agroassetId"));
        setSqlView("v_extract_hive");
        setNickname(extras.getString("nickname"));
        setDcode(extras.getString("dcode"));
        setCode(extras.getString("code").substring(7));
        super.onCreate(savedInstanceState);
    }
}
