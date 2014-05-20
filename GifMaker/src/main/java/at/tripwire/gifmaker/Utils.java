package at.tripwire.gifmaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public final class Utils {

    public static Bitmap resizeImage(Bitmap img) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String resolution = sharedPref.getString("pref_resolution", "");
        String[] res = resolution.split("x");

        Bitmap tmp = Bitmap.createScaledBitmap(img, Integer.parseInt(res[0]), Integer.parseInt(res[1]), false);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(tmp, 0, 0, tmp.getWidth(), tmp.getHeight(), matrix, true);
    }
}
