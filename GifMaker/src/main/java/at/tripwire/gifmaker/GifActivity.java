package at.tripwire.gifmaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class GifActivity extends ActionBarActivity {

    private byte[] gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);



        gif = getIntent().getByteArrayExtra("data");
        // do something with this byte array

        setContentView(new GIFView(this));

        writeFile(gif);
    }

    private void writeFile(byte[] gif) {
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream("/sdcard/test.gif");
            outStream.write(gif);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GIFView extends View {

        Movie movie, movie1;
        long moviestart;

        public GIFView(Context context) {
            super(context);

            movie = Movie.decodeByteArray(gif, 0, gif.length);
            System.out.println("\tmoviesHeight=" + movie.height());
        }

        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);
            super.onDraw(canvas);
            long now=android.os.SystemClock.uptimeMillis();
            System.out.println("now="+now);
            if (moviestart == 0) { // first time
                moviestart = now;

            }
            System.out.println("\tmoviestart="+moviestart);
            System.out.println("\tmoviesduration="+movie.duration());
            int relTime = (int)((now - moviestart) % movie.duration()) ;
            System.out.println("time="+relTime+"\treltime="+movie.duration());
            movie.setTime(relTime);
            movie.draw(canvas,this.getWidth()/2-20,this.getHeight()/2-40);
            this.invalidate();
        }
    }
}
