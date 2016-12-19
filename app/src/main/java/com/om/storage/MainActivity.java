package com.om.storage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "uuLt4EMmdnZybxTV0dq7QtA1a";
    private static final String TWITTER_SECRET = "HuznEEHtG3ZXTiMiQLEyod12yjNf82j9DMhmnB9IDuUeo2mw31";

    EditText et;
    TextView text;
    private SharedPreferences spfs;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        //spfs = PreferenceManager.getDefaultSharedPreferences(this);
        et = (EditText) findViewById(R.id.et);
        text = (TextView) findViewById(R.id.text);

        File f = Environment.getExternalStorageDirectory();
        Log.e(TAG, "onCreate: Environment.getExternalStorageDirectory()=" + f.getAbsolutePath());
    }

    public void done(View view) {
        byte b[] = et.getText().toString().getBytes();
        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("sigma.txt", MODE_PRIVATE);
            fOut.write(b, 0, b.length);
            fOut.close();
        } catch (Exception io) {
            io.printStackTrace();
        }
    }

    public void get(View view) {
        byte b[] = new byte[1024];
        int len = -1;
        FileInputStream fIn = null;
        String str = "";
        try {
            fIn = openFileInput("sigma.txt");
            while ((len = fIn.read(b)) != -1) {
                str += new String(b,0,len);
            }
            text.setText(str);
            Log.e(TAG, "get: " + str);
            fIn.close();
        } catch (Exception io) {
            io.printStackTrace();
        }

    }

    // Using PreferenceManager.getDefaultSharedPreferences
   /* public void done(View view) {
        //String email=  spfs.getString("email", "sureksd@gmail.com");
        SharedPreferences.Editor e = spfs.edit();
        e.putString("email", et.getText().toString());
        e.putBoolean("isEMail", true);
        e.commit();
    }

    public void get(View view) {
        text.setText(spfs.getString("email", "sureksd@gmail.com"));
    }*/
}
