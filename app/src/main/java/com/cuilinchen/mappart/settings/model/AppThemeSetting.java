package com.cuilinchen.mappart.settings.model;

import com.cuilinchen.mappart.Database.SettingDBManager;
import com.cuilinchen.mappart.settings.Choice;
import com.cuilinchen.mappart.settings.FlowSetting;
import com.cuilinchen.mappart.settings.SettingPrompt;
import com.cuilinchen.mappart.settings.activity.FlowSettingActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Art on 5/12/16.
 */
public class AppThemeSetting extends FlowSetting implements Choice.OnClickListener {
  @Override
  public ArrayList<Choice> getChoices() {
    return new ArrayList<>(Arrays.asList(
        new GrayTheme().addChoiceOnClickListener(_this),
        new PinkTheme().addChoiceOnClickListener(_this),
        new BlueTheme().addChoiceOnClickListener(_this)
    ));
  }
  private AppThemeSetting _this = this;

  private transient FlowSettingActivity flowSettingActivity;

  /*  Constructor  */
  public AppThemeSetting(FlowSettingActivity _flowSettingActivity){
    flowSettingActivity = _flowSettingActivity;
  }


  @Override
  public void choiceClicked(Choice c) {
    SettingDBManager.createTable(flowSettingActivity);
    SettingDBManager.deleteData("THEME");
    System.out.println("!JRKLE:FJLSD!!!!!");
    if (c instanceof BlueTheme) {
      //
      SettingDBManager.insertData("THEME", "Blue");
    } else if (c instanceof PinkTheme) {
      //
      SettingDBManager.insertData("THEME", "Pink");
    } else {
      //gray
      SettingDBManager.insertData("THEME", "Gray");
    }

    flowSettingActivity.choiceClicked(c);
    flowSettingActivity.recreate();
  }

  @Override
  public SettingPrompt getPrompt() {

    return new AppThemePrompt();
  }
}
