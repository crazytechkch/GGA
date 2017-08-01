package co.crazytech.gga.agroasset.extract;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
        TextView tvNickname;
        tvNickname = (TextView)findViewById(R.id.textViewNickName);
        tvNickname.setText(extras.getString("dcode")+". "+extras.getString("nickname")+"("+extras.getString("code").substring(5)+")");

        Long id = extras.getLong("id");
        String sqlView = extras.getString("sqlView");
        extract = new AgroassetExtract(this,id,sqlView);

        EditText etDate,etPods,etVol,etWeight,etRemark;
        etDate = (EditText)findViewById(R.id.editTextDate);
        etDate.setText(new SimpleDateFormat("yyyy/MM/dd EEE HH:mm:ss").format(new Date(extract.getDate())));
        etPods = (EditText)findViewById(R.id.editTextPods);
        etPods.setText(extract.getPodCount());
        etVol = (EditText)findViewById(R.id.editTextVolume);
        etVol.setText(extract.getVolume()+"");
        etWeight = (EditText)findViewById(R.id.editTextWeight);
        etWeight.setText(extract.getWeight()+"");
        etRemark = (EditText)findViewById(R.id.editTextRemark);
        etRemark.setText(extract.getRemark());

    }

    public AgroassetExtract getExtract() {
        return extract;
    }

    public void setExtract(AgroassetExtract extract) {
        this.extract = extract;
    }
}
