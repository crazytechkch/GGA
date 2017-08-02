package co.crazytech.gga.agroasset.extract;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.crazytech.gga.MainActivity;
import co.crazytech.gga.R;
import co.crazytech.gga.agroasset.UomSpinnerAdapter;

/**
 * Created by eric on 8/1/2017.
 */

public class AgroassetExtractEditActivity extends Activity {
    private AgroassetExtract extract;
    private Button btnPodPlus,btnPodMinus,btnOk;
    private EditText etDate,etTime,etPods,etVol,etWeight,etRemark;
    private Spinner spnWeight,spnVol;
    private boolean reqSuccess;
    private int reqCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agroasset_extract_edit);
        initView();
    }

    private void initView(){
        Bundle extras = getIntent().getExtras();
        reqCode = extras.getInt("reqCode");
        TextView tvNickname;
        tvNickname = (TextView)findViewById(R.id.textViewNickname);
        tvNickname.setText(extras.getString("dcode")+". "+extras.getString("nickname")+"("+extras.getString("code").substring(5)+")");

        Long id = extras.getLong("id");
        String sqlView = extras.getString("sqlView");
        extract = new AgroassetExtract(this,id,sqlView);

        etDate = (EditText)findViewById(R.id.editTextDate);
        etTime = (EditText)findViewById(R.id.editTextTime);
        Calendar date = Calendar.getInstance();
        try {
            if(extract.getDate()!=null)date.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(extract.getDate().replaceAll("\\p{Cntrl}", "")));
            else date.setTimeInMillis(date.getTimeInMillis()+43200000);
        } catch (ParseException e) {
            Log.w("Date Parse Error",e.getMessage());
        }


        etDate.setText(new SimpleDateFormat("dd MMM yyyy").format(date.getTime()));
        etTime.setText(new SimpleDateFormat("hh:mm:ss a").format(date.getTime()));
        etDate.setOnClickListener(dateTimeClickListenser());
        etTime.setOnClickListener(dateTimeClickListenser());

        etPods = (EditText)findViewById(R.id.editTextPods);
        if(extract.getProdTypeId()!=null){
            if(extract.getProdTypeId()==2)etPods.setText(extract.getPodCount()+"");
            else {
                LinearLayout linlayPod = (LinearLayout)findViewById(R.id.linlayPod);
                linlayPod.setVisibility(View.GONE);
            }
        }
        etVol = (EditText)findViewById(R.id.editTextVolume);
        etVol.setText(extract.getVolume()!=null?extract.getVolume().toString():null);
        etWeight = (EditText)findViewById(R.id.editTextWeight);
        etWeight.setText(extract.getWeight()!=null?extract.getWeight().toString():null);
        etRemark = (EditText)findViewById(R.id.editTextRemark);
        etRemark.setText(extract.getRemark());

        spnVol = (Spinner)findViewById(R.id.spinnerVolume);
        spnVol.setAdapter(new UomSpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,MainActivity.Uom.VOLUME));
        spnWeight = (Spinner)findViewById(R.id.spinnerWeight);
        spnWeight.setAdapter(new UomSpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,MainActivity.Uom.WIEGHT));

        btnPodPlus = (Button)findViewById(R.id.btnPlus);
        btnPodPlus.setOnClickListener(podBtnClickListener(etPods));
        btnPodPlus.setOnLongClickListener(podBtnLongClickListener(etPods));
        btnPodPlus.setOnTouchListener(podBtnTouchListener());
        btnPodMinus = (Button)findViewById(R.id.btnMinus);
        btnPodMinus.setOnClickListener(podBtnClickListener(etPods));
        btnPodMinus.setOnLongClickListener(podBtnLongClickListener(etPods));
        btnPodMinus.setOnTouchListener(podBtnTouchListener());

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                try{
                    date.setTime(new SimpleDateFormat("dd MMM yyyy hh:mm:ss a").parse(etDate.getText()+" "+etTime.getText()));
                    extract.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime()));
                } catch (ParseException e){
                    Log.w("Date Parse Exception",e.getMessage());
                }
                if(etPods.getText().toString().length()>0)extract.setPodCount(Integer.valueOf(etPods.getText()+""));
                if(etVol.getText().toString().length()>0)extract.setVolume(Double.valueOf(etVol.getText()+""));
                if(etWeight.getText().toString().length()>0)extract.setWeight(Double.valueOf(etWeight.getText()+""));
                extract.setRemark(etRemark.getText().toString());
                extract.setVolUomId(spnVol.getSelectedItemId());
                extract.setWeightUomId(spnWeight.getSelectedItemId());

                switch (reqCode){
                    case MainActivity.Request.REQ_REC_EDIT:
                        reqSuccess=extract.dbUpdate(v.getContext());
                        break;
                    case MainActivity.Request.REQ_REC_NEW:
                        Long agroassetId = getIntent().getLongExtra("agroassetId",0);
                        extract.setAgroassetId(agroassetId);
                        reqSuccess=extract.dbInsert(v.getContext());
                        break;
                    default: break;
                }
                finish();

            }
        });


    }

    private View.OnClickListener dateTimeClickListenser(){
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((EditText)v).selectAll();
                final Calendar date = Calendar.getInstance();
                try{
                    String dateStr = etDate.getText().toString()+" "+etTime.getText().toString();
                    date.setTime(new SimpleDateFormat("dd MMM yyyy hh:mm:ss a").parse(dateStr));
                } catch (ParseException e){
                    Log.w("Date Parse Error",e.getMessage());
                }
                DatePickerDialog datePicker = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,monthOfYear,dayOfMonth);
                        ((EditText) v).setText(new SimpleDateFormat("dd MMM yyyy").format(newDate.getTime()));
                    }
                },date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH));
                TimePickerDialog timePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newDate = Calendar.getInstance();
                        try{
                            newDate.setTime(new SimpleDateFormat("HH:mm").parse(String.format("%02d",hourOfDay)+":"+String.format("%02d",minute)));
                        } catch (ParseException e){
                            Log.w("Date Parse Error",e.getMessage());
                        }
                        ((EditText) v).setText(new SimpleDateFormat("hh:mm:ss a").format(newDate.getTime()));
                    }
                }, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), false);
                if(v==etDate)datePicker.show();
                else if(v==etTime)timePicker.show();
            }
        };
    }

    private View.OnClickListener podBtnClickListener(final EditText etPod){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String podStr = etPod.getText().toString();
                if (podStr!=null&&podStr.length()>0){
                    Integer podCount = Integer.valueOf(podStr);
                    if (podCount!=null){
                        if(v==btnPodPlus){
                            podCount+=1;
                        } else if(v==btnPodMinus&&podCount>0){
                            podCount-=1;
                        }
                        etPod.setText(podCount+"");
                    }
                }
            }
        };
    }

    private Handler updateHandler = new Handler();
    private Boolean updaterRunning = false;

    private View.OnLongClickListener podBtnLongClickListener(final EditText etPod){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                updaterRunning = true;
                updateHandler.postDelayed(new PodUpdater(v,etPod),500);
                return false;
            }
        };
    }

    private View.OnTouchListener podBtnTouchListener(){
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && updaterRunning ){
                    updaterRunning = false;
                }
                return false;
            }
        };
    }

    private class PodUpdater implements Runnable {
        private View view;
        private EditText etPod;

        public PodUpdater(View view, EditText etPod) {
            this.view = view;
            this.etPod = etPod;
        }

        @Override
        public void run() {
            String podStr = etPod.getText().toString();
            if(updaterRunning&&podStr!=null&&podStr.length()>0) {
                Integer podCount = Integer.valueOf(podStr);
                if (podCount != null) {
                    if (view == btnPodPlus) podCount++;
                    else if (view == btnPodMinus&&podCount>0) podCount--;
                    etPod.setText(podCount + "");
                    updateHandler.postDelayed(new PodUpdater(view, etPod), 100);
                }
            }
        }
    }

    @Override
    public void finish() {
        Log.d("REQ Success",reqSuccess+"");
        if(reqSuccess) setResult(Activity.RESULT_OK);
        else setResult(Activity.RESULT_CANCELED);
        super.finish();
    }

    public AgroassetExtract getExtract() {
        return extract;
    }

    public void setExtract(AgroassetExtract extract) {
        this.extract = extract;
    }
}
