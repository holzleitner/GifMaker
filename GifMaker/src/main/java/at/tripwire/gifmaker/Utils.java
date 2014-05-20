package at.tripwire.gifmaker;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public final class Utils {

    public static Bitmap resizeImage(Bitmap img) {
        Bitmap tmp = Bitmap.createScaledBitmap(img, 500, 400, false);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(tmp, 0, 0, tmp.getWidth(), tmp.getHeight(), matrix, true);
    }
}
