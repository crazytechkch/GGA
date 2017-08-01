package co.crazytech.gga.agroasset.tree;

import android.os.Bundle;
import android.support.annotation.Nullable;

import co.crazytech.gga.agroasset.extract.AgroassetExtractActivity;

/**
 * Created by eric on 8/1/2017.
 */

public class TreeExtractActivity extends AgroassetExtractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        setAgroassetId(extras.getLong("agroassetId"));
        setSqlView("v_extract_tree");
        setNickname(extras.getString("nickname"));
        setDcode(extras.getString("dcode"));
        setCode(extras.getString("code"));
        super.onCreate(savedInstanceState);
    }
}
