package com.cuilinchen.mappart.viewFactory;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cuilinchen.mappart.R;

/**
 * Created by CUILINCHEN on 5/8/16.
 */
public class QuestionFactory {

  public static View question(View v, Activity activity){
    TextView spinner_tv = (TextView) v.findViewById(R.id.slot_tv_spinner);
    TextView choiceslot_tv = (TextView) v.findViewById(R.id.choiceslot_textview);

    TextView edittext_tv = (TextView) v.findViewById(R.id.slot_tv_edittext);
    EditText edittext = (EditText) v.findViewById(R.id.slot_edittext);

    Typeface default_typeface = TypefaceFactory.font("Futura-Std-Light_19054.ttf", activity);
    if(spinner_tv != null)
      spinner_tv.setTypeface(default_typeface);
    if(choiceslot_tv != null)
      choiceslot_tv.setTypeface(default_typeface);
    if(edittext_tv != null)
      edittext_tv.setTypeface(default_typeface);
    if(edittext != null)
      edittext.setTypeface(default_typeface);

    return v;
  }

}
