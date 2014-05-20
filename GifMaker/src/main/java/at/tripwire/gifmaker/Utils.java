package at.tripwire.gifmaker;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import at.tripwire.gifmaker.activity.SettingsActivity;

public final class Utils {

    public static Bitmap resizeImage(Bitmap img) {


        return Bitmap.createScaledBitmap(img, 200, 150, false);
    }
}
