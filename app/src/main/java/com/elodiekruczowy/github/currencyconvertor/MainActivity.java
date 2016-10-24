package com.elodiekruczowy.github.currencyconvertor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    final String DEBUG_TAG = "LOG";
    // Graphical elements from the Activity
    TextView convert_in, convert_out;
    Button convert_but;
    Spinner source_spin, destination_spin;
    // Conversion rates
    HashMap<String, Float> conversion_rates;
    // Currency symbols
    HashMap<String, String> currency_symbols;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(DEBUG_TAG, "onStart() function entered");
    // BEGIN Views intializations
        // Button to convert values
        convert_but = (Button) findViewById(R.id.button_convert);
        convert_but.setOnClickListener(new convertButtonListener(this));
        // Text field for the user to write the value to convert
        convert_in = (TextView) findViewById(R.id.textfield_convIn);
        // Text field for displaying the cinverted value
        convert_out = (TextView) findViewById(R.id.textfield_convRes);
        // Spinner to select currencies
        source_spin = (Spinner) findViewById(R.id.spinner_sourceCurrency);
        destination_spin = (Spinner) findViewById(R.id.spinner_destCurrency);
    // END Views intialization
        // Conversion rates initialization
        // Rates are from foreign currency to euro
        conversion_rates = new HashMap<String, Float>();
        conversion_rates.put("Euro", 1f);
        conversion_rates.put("Dollar", 1.42f);
        conversion_rates.put("Peso", 0.75f);
        conversion_rates.put("Yen", 1.25f);
        // Currency symbols initialization
        currency_symbols = new HashMap<String, String>();
        currency_symbols.put("Euro", "€");
        currency_symbols.put("Dollar", "$");
        currency_symbols.put("Peso", "$");
        currency_symbols.put("Yen", "¥");
    }

    public void updateConversionValue() {
    // Rates calculation based on source & destination currencies
        float rate, source_rate, destination_rate;
        String source_currency, destination_currency;
        // Source to euro rate
        source_currency = source_spin.getSelectedItem().toString();
        source_rate = conversion_rates.get(source_currency);
        // Euro to destination rate
        destination_currency = destination_spin.getSelectedItem().toString();
        destination_rate = 1 / conversion_rates.get(destination_currency);
        // Final rate
        rate = source_rate * destination_rate;

    // Currency symbol
        String currency_symbol;
        currency_symbol = currency_symbols.get(destination_currency);

    // Numeric input from user
        String txtValue;
        float decValue;
        txtValue = convert_in.getText().toString();
        try {
            decValue = Float.parseFloat(txtValue);
            txtValue = (decValue * rate) + currency_symbol;
        }
        catch(Exception e) {
            txtValue = "Wrong input! (NaN)";
        }
        convert_out.setText(txtValue);
    }
}
