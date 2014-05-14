package at.tripwire.gifmaker.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;

import at.tripwire.gifmaker.R;
import at.tripwire.gifmaker.view.GifView;

public class GifActivity extends ActionBarActivity {

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gif);

        byte[] gif = getIntent().getByteArrayExtra("data");
        setContentView(new GifView(this, gif));

        //writeFile(gif);

        saveButton = (Button) findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void writeFile(byte[] gif) {
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream("/sdcard/test2.gif");
            outStream.write(gif);
            outStream.close();
        } catch (Exception e) {
            Log.e("GifActivity", e.getMessage());
        }
    }
}
