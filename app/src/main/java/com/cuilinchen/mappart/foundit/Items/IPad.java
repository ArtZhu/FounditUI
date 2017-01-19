package com.cuilinchen.mappart.foundit.Items;

import com.cuilinchen.mappart.foundit.Questionnaire.ChoiceQuestion;
import com.cuilinchen.mappart.foundit.Questionnaire.Question;
import com.cuilinchen.mappart.foundit.Questionnaire.StringQuestion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by CUILINCHEN on 5/11/16.
 */
public class IPad extends Item {
  private IPad _this = this;

  private String category;
  private String screen_cond;

  public static final IPad dummy_ipad = (IPad) new IPad().category("PRO").screenCondition("splintered screen").color("Black");
  @Override
  public String type() {
    return "IPad";
  }

  private String[] screenstrings = {
      "scratched screen",
      "perfect screen",
      "cracked screen",
      "splintered screen",
      "screen barely exist"
  };

  private String[] categorystrings = {
      "IPAD PRO",
      "IPAD MINI",
      "JUST AN IPAD"
  };

  @Override
  public String generateRandomDetail() {
    Random r = new Random();
    return "IT'S AN " + categorystrings[r.nextInt(categorystrings.length)] + "!" + (screenstrings[r.nextInt(screenstrings.length)]);
  }

  public IPad category(String _category){
    category = _category;
    return _this;
  }

  public IPad screenCondition(String _screen_cond){
    screen_cond = _screen_cond;
    return _this;
  }

  @Override
  public void init_questionnaire() {
    try {
      questionnaire.put(new StringQuestion("What's the color?", "color")
          , getClass().getMethod("color", String.class));
      questionnaire.put(new ChoiceQuestion<String>("Is it IPad Pro, Mini or neither?", "category")
          .putChoice("Pro")
          .putChoice("Mini")
          .putChoice("Neither")
          , getClass().getMethod("category", String.class)
      );
      questionnaire.put(new StringQuestion("Describe the screen condition", "screen Condition")
          , getClass().getMethod("screenCondition", String.class));
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Item copyOf(boolean copy_attributes) {
    IPad ret = new IPad().category("PRO").screenCondition("splintered screen");
    if(copy_attributes)
      ret.category(category).screenCondition(screen_cond);
    return ret;
  }

  @Override
  public Item fromJSON(JSONObject jsonObject) throws JSONException {
    return null;
  }
}
