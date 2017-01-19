package com.cuilinchen.mappart.viewFactory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by CUILINCHEN on 5/6/16.
 */
public class TypefaceFactory {

  public static Typeface font(String file_path, Context context){
    Typeface tf = Typeface.createFromAsset(
        context.getAssets(), file_path
    );
  return tf;
  }

  public static TextView format(TextView tv, String file_path, Context context){
    tv.setTypeface(font(file_path, context));
    return tv;
  }
}
