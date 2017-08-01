package co.crazytech.gga.agroasset.extract;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.crazytech.gga.R;

/**
 * Created by eric on 8/1/2017.
 */

public class AgroassetExtractEditActivity extends Activity {
    private AgroassetExtract extract;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset_extract_edit);
    }

    private void initView(){
        Bundle extras = getIntent().getExtras();
        TextView tvId,tvNickname;
        tvId = (TextView)findViewById(R.id.textViewDcode);
        tvId.setText(extras.getString("dcode"));
        tvNickname = (TextView)findViewById(R.id.textViewNickName);
        tvNickname.setText(extras.getString("nickname")+"("+extras.getString("code")+")");

        EditText etDate,etPods,etVol,etWeight;
        etDate = (EditText)findViewById(R.id.editTextDate);

    }

    public AgroassetExtract getExtract() {
        return extract;
    }

    public void setExtract(AgroassetExtract extract) {
        this.extract = extract;
    }
}
