package co.crazytech.gga.camera;

import android.app.Activity;
import android.graphics.Camera;
import android.os.Handler;
import android.widget.Button;

/**
 * Created by eric on 30/7/2017.
 */

public class CameraActivity extends Activity {
    private Camera camera;
    private Handler afHandler;

    private Button captureButton;

    private void initControls() {
        afHandler = new Handler();
    }

    
}
