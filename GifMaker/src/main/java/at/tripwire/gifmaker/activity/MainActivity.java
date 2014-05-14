package at.tripwire.gifmaker.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import at.tripwire.gifmaker.R;
import at.tripwire.gifmaker.Utils;
import at.tripwire.gifmaker.encoder.AnimatedGifEncoder;
import at.tripwire.gifmaker.view.CameraPreview;

public class MainActivity extends ActionBarActivity {

    private static final int MAX_IMAGES = 10;

    private static final int LOAD_IMAGE_RESULTS = 1;

    private Button captureButton;

    private Button createButton;

    private Button clearButton;

    private Button loadButton;

    private Camera camera;

    private List<Bitmap> images;

    private ProgressBar progressBar;

    private TextView progressbarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new ArrayList<Bitmap>(MAX_IMAGES);

        captureButton = (Button) findViewById(R.id.capture_button);
        createButton = (Button) findViewById(R.id.create_button);
        clearButton = (Button) findViewById(R.id.clear_button);
        loadButton = (Button) findViewById(R.id.load_button);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(MAX_IMAGES);
        progressbarText = (TextView) findViewById(R.id.progressbar_text);

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
                            images.add(Utils.resizeImage(tmp));

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
                gifTask.execute(images);
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
            }
        });

        refreshUi();
    }

    private void refreshUi() {
        captureButton.setEnabled(images.size() < MAX_IMAGES);
        createButton.setEnabled(images.size() > 0);
        clearButton.setEnabled(images.size() > 0);
        loadButton.setEnabled(images.size() < MAX_IMAGES);
        progressBar.setProgress(images.size());
        progressbarText.setText(images.size() + "/" + MAX_IMAGES);
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
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            try {
                Uri pickedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(pickedImage);
                Bitmap img = BitmapFactory.decodeStream(imageStream);

                images.add(Utils.resizeImage(img));

                refreshUi();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private AsyncTask<List<Bitmap>, Integer, byte[]> gifTask = new AsyncTask<List<Bitmap>, Integer, byte[]>() {
        @Override
        protected byte[] doInBackground(List<Bitmap>... lists) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.start(bos);
            for (Bitmap bitmap : images) {
                encoder.addFrame(bitmap);
                encoder.setDelay(10);
            }
            encoder.finish();
            images.clear();
            return bos.toByteArray();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // TODO show dialog
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // TODO show progress in dialog
        }

        @Override
        protected void onPostExecute(byte[] gif) {
            super.onPostExecute(gif);
            // TODO hide dialog

            // start GifActivity
            Intent intent = new Intent(MainActivity.this, GifActivity.class);
            intent.putExtra("data", gif);
            startActivity(intent);
        }
    };
}
