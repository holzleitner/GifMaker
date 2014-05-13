package at.tripwire.gifmaker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class GifActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        byte[] gif = getIntent().getByteArrayExtra("data");
        // do something with this byte array
    }
}
