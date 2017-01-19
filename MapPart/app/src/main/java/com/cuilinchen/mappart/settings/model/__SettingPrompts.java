package com.cuilinchen.mappart.settings.model;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuilinchen.mappart.settings.Setting;
import com.cuilinchen.mappart.settings.SettingPrompt;
import com.cuilinchen.mappart.viewFactory.SettingFactory;

/**
 * Created by Art on 5/5/16.
 */
public class __SettingPrompts {
}

/*  HandPreference */
class HandPreferencePrompt implements SettingPrompt {
  private final String PROMPT_STRING = "ARE YOU";
  @Override
  public View getView(Activity activity) {
    LinearLayout drawing_pane = new LinearLayout(activity);
    TextView t = new TextView(activity);
    t.setText(PROMPT_STRING);
    t.setId(SettingPrompt.TEXT_TITLE);

    drawing_pane.addView(t);

    return SettingFactory.Prompt(activity, drawing_pane);
  }
}

