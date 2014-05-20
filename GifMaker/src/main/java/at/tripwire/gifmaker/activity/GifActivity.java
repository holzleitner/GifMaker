package at.tripwire.gifmaker.activity;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.io.FileOutputStream;

import at.tripwire.gifmaker.R;
import at.tripwire.gifmaker.view.GifMovieView;
import at.tripwire.gifmaker.view.GifView;

public class GifActivity extends ActionBarActivity {

    private RelativeLayout gifLayout;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        byte[] gif = getIntent().getByteArrayExtra("data");

        GifMovieView view = new GifMovieView(this);
        view.setMovie(Movie.decodeByteArray(gif, 0, gif.length));

        gifLayout = (RelativeLayout) findViewById(R.id.gifLayout);
        gifLayout.addView(view);

        //writeFile(gif);
    }

    private void writeFile(byte[] gif) {
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream("/sdcard/test2.gif");
            outStream.write(gif);
            outStream.close();
        } catch (Exception e) {
            Log.e("GifActivity", e.getMessage());
        }
    }
}
