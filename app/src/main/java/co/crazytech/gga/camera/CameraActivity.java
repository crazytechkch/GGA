package co.crazytech.gga.camera;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.crazytech.gga.R;

/**
 * Created by eric on 30/7/2017.
 */

public class CameraActivity extends FragmentActivity {
    private Camera camera;
    private Handler afHandler;
    private CameraPreview camPreview;
    private Boolean previewing,isFlashOn;

    private FloatingActionButton fabCamera,fabFlash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.camera);
        initControls();

    }

    private void initControls() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        afHandler = new Handler();
        camera = getCameraInstance();

         if (camera!=null){
             previewing = true;
            camPreview = new CameraPreview(this,camera,previewCb,autoFocusCB);
            FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
            preview.addView(camPreview);
        }

        fabCamera = (FloatingActionButton) findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.setPreviewCallback(previewCb);
                camera.startPreview();
                previewing = true;
                camera.autoFocus(autoFocusCB);
            }
        });

        fabFlash = (FloatingActionButton) findViewById(R.id.fabFlash);
        fabFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlash(v);
            }
        });
    }

    private Camera getCameraInstance() {
        Camera cam = null;
        try {
            cam = Camera.open();
        } catch (Exception e) {
            Log.w("Camera",e.getMessage());
        }
        return cam;
    }

    private void releaseCamera() {
        if (camera != null) {
            previewing = false;
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    protected void toggleFlash(View v){
        FloatingActionButton fab = (FloatingActionButton)v;
        if (isFlashOn) {
            fab.setImageResource(R.drawable.ic_flash_off_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_flash_on_white_24dp);
        }
        isFlashOn = !isFlashOn;

    }


    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            afHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                camera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

//             previewing = false;
//            camera.setPreviewCallback(null);
//            camera.stopPreview();
//            finish();
        }
    };

}
