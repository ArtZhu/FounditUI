package com.cuilinchen.mappart.settings.model;

import com.cuilinchen.mappart.settings.Choice;
import com.cuilinchen.mappart.settings.FlowSetting;
import com.cuilinchen.mappart.settings.SettingPrompt;
import com.cuilinchen.mappart.settings.activity.FlowSettingActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Art on 5/5/16.
 */
public class HandPreference extends FlowSetting implements Choice.OnClickListener{
  private HandPreference _this = this;

  private FlowSettingActivity flowSettingActivity;
  private ArrayList<Choice> choices = new ArrayList<>(Arrays.asList(
      new LeftHand().addChoiceOnClickListener(_this),
      new RightHand().addChoiceOnClickListener(_this)
  ));

  /*  Constructor  */
  public HandPreference(FlowSettingActivity _flowSettingActivity){
    flowSettingActivity = _flowSettingActivity;
  }

  private SettingPrompt prompt = new HandPreferencePrompt();
  @Override
  public ArrayList<Choice> getChoices() {
    return choices;
  }

  @Override
  public SettingPrompt getPrompt() {
    return prompt;
  }


  /*******************
   *
   * @param c
   */
  @Override
  public void choiceClicked(Choice c) {
    //IMPLEMENT THIS!
    System.out.println("EQUALS:" + c.getClass().equals(LeftHand.class));

    flowSettingActivity.choiceClicked(c);
  }

}
