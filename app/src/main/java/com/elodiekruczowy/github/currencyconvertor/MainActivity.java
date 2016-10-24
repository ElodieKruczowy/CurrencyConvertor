package com.elodiekruczowy.github.currencyconvertor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    final String DEBUG_TAG = "LOG";
    // Graphical elements from the Activity
    TextView convert_in, convert_out;
    Button convert_but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(DEBUG_TAG, "onStart() function entered");
        convert_but = (Button) findViewById(R.id.button_convert);
        convert_in = (TextView) findViewById(R.id.textfield_convIn);
        convert_out = (TextView) findViewById(R.id.textfield_convRes);
    }
}
