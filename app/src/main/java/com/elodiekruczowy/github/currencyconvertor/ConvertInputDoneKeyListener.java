package com.elodiekruczowy.github.currencyconvertor;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

public class ConvertInputDoneKeyListener implements TextView.OnEditorActionListener{
    private MainActivity extActivity;

    public ConvertInputDoneKeyListener(MainActivity act) {
        extActivity = act;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            extActivity.updateConversionValue();
        }
        return false;
    }
}