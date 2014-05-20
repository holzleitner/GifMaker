package at.tripwire.gifmaker;

import android.graphics.Bitmap;

public final class Utils {

    public static Bitmap resizeImage(Bitmap img) {
        return Bitmap.createScaledBitmap(img, 200, 150, false);
    }
}
