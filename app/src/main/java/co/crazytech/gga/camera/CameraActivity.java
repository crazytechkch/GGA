package co.crazytech.gga.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import co.crazytech.gga.R;

/**
 * Created by eric on 30/7/2017.
 */

public class CameraActivity extends Activity {
    private Camera camera;
    private Handler afHandler;
    private CameraPreview camPreview;
    private Boolean previewing;

    private FloatingActionButton fabCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        initControls();

    }

    private void initControls() {

        afHandler = new Handler();
        try{
            camera = Camera.open();
        } catch (Exception e){
            Log.w("Camera",e.getMessage());
        }
        if (camera!=null){
            camPreview = new CameraPreview(this,camera,previewCb,autoFocusCB);
            FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
            preview.addView(camPreview);
        }

        fabCamera = (FloatingActionButton)findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.setPreviewCallback(previewCb);
                camera.startPreview();
                previewing = true;
                camera.autoFocus(autoFocusCB);
            }
        });
    }

    private void releaseCamera() {
        if (camera != null) {
            previewing = false;
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
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

             previewing = false;
            camera.setPreviewCallback(null);
            camera.stopPreview();
            finish();
        }
    };
}
