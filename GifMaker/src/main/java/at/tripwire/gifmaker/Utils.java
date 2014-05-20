package at.tripwire.gifmaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;

import at.tripwire.gifmaker.activity.MainActivity;

public final class Utils {

    public static Bitmap resizeImage(Bitmap img, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String resolution = sharedPref.getString("pref_resolution", "");
        String[] res = resolution.split("x");

        return Bitmap.createScaledBitmap(img, Integer.parseInt(res[0]), Integer.parseInt(res[1]), false);
    }
}
