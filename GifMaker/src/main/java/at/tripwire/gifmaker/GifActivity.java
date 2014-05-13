package at.tripwire.gifmaker;

import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.io.InputStream;


public class GifActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        setContentView(new GIFView(this));
    }

    private class GIFView extends View {

        Movie movie,movie1;
        InputStream is=null,is1=null;
        long moviestart;

        public GIFView(Context context) {
            super(context);

            is=context.getResources().openRawResource(R.drawable);
            movie=Movie.decodeStream(is);
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
            int relTime = (int)((now - moviestart) % movie.duration()) ;
            System.out.println("time="+relTime+"\treltime="+movie.duration());
            movie.setTime(relTime);
            movie.draw(canvas,this.getWidth()/2-20,this.getHeight()/2-40);
            this.invalidate();
        }
    }
}
