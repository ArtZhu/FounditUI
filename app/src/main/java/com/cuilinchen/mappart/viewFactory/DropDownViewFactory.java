package com.cuilinchen.mappart.viewFactory;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.cuilinchen.mappart.R;

/**
 * Created by CUILINCHEN on 5/8/16.
 */
public class DropDownViewFactory {

  public static View dropDownView(View v, Activity activity){
    TextView tv = (TextView) v.findViewById(R.id.itemtype_textview);

    Typeface default_typeface = TypefaceFactory.font("Futura-Std-Light_19054.ttf", activity);
    tv.setTypeface(default_typeface);

    DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
    float dp = activity.getResources().getDimension(R.dimen.dropdowntext_size);
    float fpixels = metrics.density * dp;
    int pixels = (int) (fpixels + 0.5f);

    tv.setTextSize(pixels);

    return v;
  }
}
