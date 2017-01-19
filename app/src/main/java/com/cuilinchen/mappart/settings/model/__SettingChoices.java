package com.cuilinchen.mappart.settings.model;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.settings.Choice;
import com.cuilinchen.mappart.viewFactory.SettingFactory;

/**
 * Created by CUILINCHEN on 5/5/16.
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

class PinkTheme extends Choice{
  private final String CHOICE_STRING = "Pink";

  @Override
  public View getView(Activity activity) {
    LinearLayout drawing_pane = new LinearLayout(activity);

    TextView tv = new TextView(activity);
    tv.setText(CHOICE_STRING);
    tv.setId(Choice.TEXT_TITLE);
    tv.setBackgroundColor(R.color.pink_user_choice);

    drawing_pane.addView(tv);
    return SettingFactory.Choices(activity, drawing_pane);
  }
}

class BlueTheme extends Choice{
  private final String CHOICE_STRING = "Blue";

  @Override
  public View getView(Activity activity) {
    LinearLayout drawing_pane = new LinearLayout(activity);


    TextView tv = new TextView(activity);
    tv.setText(CHOICE_STRING);
    tv.setId(Choice.TEXT_TITLE);
    tv.setBackgroundColor(R.color.blue_user_choice);

    drawing_pane.addView(tv);
    return SettingFactory.Choices(activity, drawing_pane);
  }
}

class GrayTheme extends Choice{
  private final String CHOICE_STRING = "Gray";

  @Override
  public View getView(Activity activity) {
    LinearLayout drawing_pane = new LinearLayout(activity);


    TextView tv = new TextView(activity);
    tv.setText(CHOICE_STRING);
    tv.setId(Choice.TEXT_TITLE);
    tv.setBackgroundColor(R.color.gray_default);

    drawing_pane.addView(tv);
    return SettingFactory.Choices(activity, drawing_pane);
  }
}



