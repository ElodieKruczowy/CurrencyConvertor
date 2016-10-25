package com.elodiekruczowy.github.currencyconvertor;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import static java.lang.Float.parseFloat;
import static java.util.Arrays.asList;


public class MainActivity extends AppCompatActivity {
    // Graphical elements from the Activity
    TextView convert_in, convert_out;
    Button convert_but;
    Spinner source_spin, destination_spin;
    // XML parsed document ontaining conversion rates
    Document currency_rates_xml;
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
        Log.d("LOG", "onStart() function entered");
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
        getCurrencyRates("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
        // Currency symbols initialization
        currency_symbols = new HashMap<String, String>();
        currency_symbols.put("EUR", "€");
        currency_symbols.put("USD", "$");
        currency_symbols.put("MXN", "$");
        currency_symbols.put("JPY", "¥");
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
            decValue = parseFloat(txtValue);
            txtValue = (decValue * rate) + currency_symbol;
        }
        catch(Exception e) {
            txtValue = "Wrong input! (NaN)";
        }
        convert_out.setText(txtValue);
    }

    void getCurrencyRates(String url) {
        NodeList list;
        AsyncTask task = new getOnlineCurrencyRates();
        task.execute(url);
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class getOnlineCurrencyRates extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                String url = (String) params[0];
                URL rates_url = new URL(url);
                InputSource rates_source = new InputSource(rates_url.openStream());
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document d = db.parse(rates_source);
                d.getDocumentElement().normalize();
                NodeList list = d.getElementsByTagName("Cube");
                String currencies_arr[] = getResources().getStringArray(R.array.currencies_list);
                HashMap<String, Float> hm = new HashMap<String, Float>();
                for (int i = 0; i < list.getLength(); i++) {
                    Node temp_node = list.item(i);
                    if (temp_node.getNodeType() == Node.ELEMENT_NODE) {
                        Element temp_element = (Element) temp_node;
                        String currency_name = temp_element.getAttribute("currency");
                        if(asList(currencies_arr).contains(currency_name))
                            hm.put(currency_name, 1/parseFloat(temp_element.getAttribute("rate")));
                    }
                }
                hm.put("EUR", 1f);
                conversion_rates = hm;
                return null;
            } catch (MalformedURLException e) {
                Log.d("LOG", "URL of the file is malformed");
                return null;
            } catch (IOException e) {
                Log.d("LOG", "Unable to open URL");
                return null;
            } catch (ParserConfigurationException e) {
                Log.d("LOG", "Problem while creating DocumentBuilder");
                return null;
            } catch (SAXException e) {
                Log.d("LOG", "Problem while parsing XML source");
                return null;
            }
        }
    }
}
