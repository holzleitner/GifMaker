package at.tripwire.gifmaker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.view.View;

public class GifView extends View {

    private Movie movie;

    private long start;

    public GifView(Context context, byte[] gif) {
        super(context);
        movie = Movie.decodeByteArray(gif, 0, gif.length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        long now = android.os.SystemClock.uptimeMillis();
        if (start == 0) { // first time
            start = now;
        }
        int relTime = (int) ((now - start) % movie.duration());
        movie.setTime(relTime);
        movie.draw(canvas, this.getWidth() / 2 - 20, this.getHeight() / 2 - 40);
        this.invalidate();
    }
}