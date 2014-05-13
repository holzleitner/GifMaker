package at.tripwire.gifmaker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final int MAX_IMAGES = 10;

    private Button captureButton;

    private Button createButton;

    private Button clearButton;

    private Button loadButton;

    private Camera camera;

    private List<Bitmap> images;

    private static int LOAD_IMAGE_RESULTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new ArrayList<Bitmap>(MAX_IMAGES);

        captureButton = (Button) findViewById(R.id.capture_button);
        createButton = (Button) findViewById(R.id.create_button);
        clearButton = (Button) findViewById(R.id.clear_button);
        loadButton = (Button) findViewById(R.id.load_button);

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            camera = getCameraInstance(this);
            camera.setDisplayOrientation(90);

            CameraPreview cameraPreview = new CameraPreview(this, camera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(cameraPreview);

            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] bytes, Camera camera) {
                            Bitmap tmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            Bitmap resized = Bitmap.createScaledBitmap(tmp, 200, 200, false);
                            images.add(resized);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Wohoo, new picture!", Toast.LENGTH_SHORT).show();
                                    refreshUi();
                                }
                            });
                            camera.startPreview();
                        }
                    });
                }
            });
        }

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO create gif in background task

                byte[] gifBytes = new byte[0];

                // start GifActivity
                Intent intent = new Intent(MainActivity.this, GifActivity.class);
                intent.putExtra("data", gifBytes);
                startActivity(intent);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (images != null) {
                    images.clear();
                }
                refreshUi();
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, LOAD_IMAGE_RESULTS);

                refreshUi();
            }

        });

        refreshUi();
    }

    private void refreshUi() {
        captureButton.setEnabled(images.size() < MAX_IMAGES);
        createButton.setEnabled(images.size() > 0);
        clearButton.setEnabled(images.size() > 0);
        loadButton.setEnabled(images.size() < MAX_IMAGES);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
