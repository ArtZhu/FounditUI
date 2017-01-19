package com.cuilinchen.mappart.viewFactory;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.settings.Choice;
import com.cuilinchen.mappart.settings.SettingPrompt;

/**
 * Created by CUILINCHEN on 5/6/16.
 */
public class SettingFactory {

  public static View Choices(Activity activity, View v){
    Object title = v.findViewById(Choice.TEXT_TITLE);
    Object content = v.findViewById(Choice.TEXT_CONTENT);
    Object image = v.findViewById(Choice.IMAGE_CONTENT);
    if(title != null){
      TextView tv = (TextView) title;
      tv.setLayoutParams(new LinearLayout.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT,
          1.0f
      ));
      tv.setTextSize(v.getResources().getDimension(R.dimen.choice_text_title_textsize));
      tv.setGravity(Gravity.CENTER);

      tv.setTypeface(TypefaceFactory.font("Futura-Std-Light_19054.ttf", activity));
      System.out.println("HERE");
    }
    return v;
  }

  ///////////SETUP SETTING PROMPT

  public static View Prompt(Activity activity, View v){
    Object title = v.findViewById(SettingPrompt.TEXT_TITLE);
    Object content = v.findViewById(SettingPrompt.TEXT_CONTENT);
    Object image = v.findViewById(SettingPrompt.IMAGE_CONTENT);
    if(title != null){
      TextView tv = (TextView) title;
      tv.setLayoutParams(new LinearLayout.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT,
          1.0f
      ));
      tv.setTextSize(v.getResources().getDimension(R.dimen.choice_text_title_textsize));
      tv.setGravity(Gravity.CENTER);

      tv.setTypeface(TypefaceFactory.font("Futura-Std-Light_19054.ttf", activity));
      System.out.println("HERE");
    }
    return v;
  }
}
