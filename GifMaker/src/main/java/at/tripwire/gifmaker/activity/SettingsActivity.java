package at.tripwire.gifmaker.activity;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import at.tripwire.gifmaker.R;

public class SettingsActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_settings);

        ListPreference listPreferenceResolution = (ListPreference) findPreference("pref_resolution");
        if(listPreferenceResolution.getValue() == null) {
            listPreferenceResolution.setValueIndex(1);
        }
        listPreferenceResolution.setSummary(listPreferenceResolution.getValue().toString());
        listPreferenceResolution.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());
                return true;
            }
        });

        ListPreference listPreferenceMaxImgNumber = (ListPreference) findPreference("pref_maxImgNumber");
        if(listPreferenceMaxImgNumber.getValue() == null) {
            listPreferenceMaxImgNumber.setValueIndex(0);
        }
        listPreferenceMaxImgNumber.setSummary(listPreferenceMaxImgNumber.getValue().toString());
        listPreferenceMaxImgNumber.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());

                setResult(RESULT_OK);

                return true;
            }
        });

        ListPreference listPreferenceDelay = (ListPreference) findPreference("pref_delay");
        if(listPreferenceDelay.getValue() == null) {
            listPreferenceDelay.setValueIndex(3);
        }
        listPreferenceDelay.setSummary(listPreferenceDelay.getValue().toString());
        listPreferenceDelay.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());
                return true;
            }
        });
    }
}
