package at.tripwire.gifmaker;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public final class Utils {

    public static Bitmap resizeImage(Bitmap img) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(img, 0, 0, 500, 350, matrix, true);
    }
}
