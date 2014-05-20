package at.tripwire.gifmaker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Movie;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;

import at.tripwire.gifmaker.R;
import at.tripwire.gifmaker.view.GifMovieView;

public class GifActivity extends ActionBarActivity {

    private Button saveButton;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        final byte[] gif = getIntent().getByteArrayExtra("data");

        GifMovieView view = (GifMovieView) findViewById(R.id.gifView);
        view.setMovie(Movie.decodeByteArray(gif, 0, gif.length));

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save as:");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String filename = input.getText().toString();
                writeFile(gif, filename);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    private void writeFile(byte[] gif, String filename) {
        FileOutputStream outStream = null;
        try {
            File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(pictureFolder, filename);
            pictureFolder.mkdirs();

            outStream = new FileOutputStream(file);
            outStream.write(gif);
            outStream.flush();
            outStream.close();

            MediaScannerConnection.scanFile(this,
                    new String[]{file.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    }
            );
        } catch (Exception e) {
            Log.e("GifActivity", e.getMessage());
        }
    }
}
