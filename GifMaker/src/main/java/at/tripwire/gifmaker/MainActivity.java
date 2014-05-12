package at.tripwire.gifmaker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Button captureButton;

    private Button loadButton;

    private Button createGifButton;

    private LinearLayout thumbnailLayout;

    private Camera camera;

    private CameraPreview cameraPreview;

    private List<Bitmap> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new ArrayList<Bitmap>();

        captureButton = (Button) findViewById(R.id.capture);
        loadButton = (Button) findViewById(R.id.load);
        createGifButton = (Button) findViewById(R.id.createButton);

        thumbnailLayout = (LinearLayout) findViewById(R.id.thumbnailLayout);

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            camera = getCameraInstance(this);
            camera.setDisplayOrientation(90);

            cameraPreview = new CameraPreview(this, camera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(cameraPreview);

            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] bytes, Camera camera) {
                            Bitmap tmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            images.add(tmp);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Wohoo, new picture!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private static Camera getCameraInstance(Context context) {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            Toast.makeText(context, "Camera is not available...", Toast.LENGTH_LONG).show();
        }
        return c;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
        }
    }
}
