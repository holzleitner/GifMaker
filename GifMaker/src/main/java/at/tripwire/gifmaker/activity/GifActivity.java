package at.tripwire.gifmaker.activity;

import android.graphics.Movie;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.io.FileOutputStream;

import at.tripwire.gifmaker.R;
import at.tripwire.gifmaker.view.GifMovieView;

public class GifActivity extends ActionBarActivity {

    private RelativeLayout gifLayout;

    private Button saveButton;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        final byte[] gif = getIntent().getByteArrayExtra("data");

        GifMovieView view = new GifMovieView(this);
        view.setMovie(Movie.decodeByteArray(gif, 0, gif.length));

        gifLayout = (RelativeLayout) findViewById(R.id.gifLayout);
        gifLayout.addView(view);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }

        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Save as:");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                writeFile(gif);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog = builder.create();
    }

    private void writeFile(byte[] gif) {
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream("/sdcard/test.gif");
            outStream.write(gif);
            outStream.close();
        } catch (Exception e) {
            Log.e("GifActivity", e.getMessage());
        }
    }
}
