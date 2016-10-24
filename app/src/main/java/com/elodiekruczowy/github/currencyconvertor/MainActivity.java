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
    // Conversion rate
    float rate = 1.42f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(DEBUG_TAG, "onStart() function entered");
        // Button to convert values
        convert_but = (Button) findViewById(R.id.button_convert);
        convert_but.setOnClickListener(new convertButtonListener(this));

        convert_in = (TextView) findViewById(R.id.textfield_convIn);
        convert_out = (TextView) findViewById(R.id.textfield_convRes);
    }

    public void updateConversionValue() {
        String txtValue;
        float decValue;
        txtValue = (convert_in.getText()).toString();
        try {
            decValue = Float.parseFloat(txtValue);
            txtValue = (decValue * rate) + "€";
        }
        catch(Exception e) {
            txtValue = "Wrong input! (NaN)";
        }
        convert_out.setText(txtValue);
    }
}
