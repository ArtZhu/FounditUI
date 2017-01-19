package com.cuilinchen.mappart.settings.model;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuilinchen.mappart.settings.Choice;
import com.cuilinchen.mappart.viewFactory.SettingFactory;

/**
 * Created by Art on 5/5/16.
 */
public class __SettingChoices {
}

/*  HandPreference */
class LeftHand extends Choice {
  private final String CHOICE_STRING = "left";

  @Override
  public View getView(Activity activity) {
    LinearLayout drawing_pane = new LinearLayout(activity);

    TextView tv = new TextView(activity);
    tv.setText(CHOICE_STRING);
    tv.setId(Choice.TEXT_TITLE);

    drawing_pane.addView(tv);
    return SettingFactory.Choices(activity, drawing_pane);
  }
}

class RightHand extends Choice{
  private final String CHOICE_STRING = "right";

  @Override
  public View getView(Activity activity) {
    LinearLayout drawing_pane = new LinearLayout(activity);

    TextView tv = new TextView(activity);
    tv.setText(CHOICE_STRING);
    tv.setId(Choice.TEXT_TITLE);

    drawing_pane.addView(tv);
    return SettingFactory.Choices(activity, drawing_pane);
  }
}


