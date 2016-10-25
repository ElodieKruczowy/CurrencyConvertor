package com.elodiekruczowy.github.currencyconvertor;

import android.util.Log;
import android.view.View;


public class convertButtonListener implements View.OnClickListener {
    MainActivity parent_activity;

    convertButtonListener(MainActivity act){
        parent_activity = act;
    }

    @Override
    public void onClick(View v) {
        Log.d("LOG", "Hello World!");
        parent_activity.updateConversionValue();
    }
}
