package com.cuilinchen.mappart.foundit.Items;

import android.graphics.Color;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by CUILINCHEN on 5/1/2016.
 */
public class PhoneCase extends ItemAddOn<Phone>{
  private final PhoneCase _this = this;

  private String color;

  public PhoneCase(Phone _parent) {
    super(_parent);
  }

  @Override
  public String type() {
    return "PhoneCase";
  }

  @Override
  public String generateRandomDetail() {
    int i = random.nextInt() % color_names.size();
    return color_names.get(i) + " " + type();
  }

  @Override
  public void init_questionnaire() {
    //no need for now
  }

  private final Random random = new Random();
  private final List<String> color_names = Arrays.asList("Black", "Blue", "Green", "Yellow", "Red", "Orange", "Purple", "White", "Grey");

  public PhoneCase color(String _color) {
    return (PhoneCase) super.color(_color);
  }

  @Override
  public ItemAddOn<Phone> copyOf(boolean copy_attributes) {
    PhoneCase ret = new PhoneCase(parent);
    if(copy_attributes){
      ret.color(color);
    }
    //!!
    return ret;
  }

////////////////////////////////////////////////////////////////////////////////////////
  //    JSON
  //
  public class JSON{
    public static final String COLOR_STRING = "Color";
    public static final String PARENT_STRING = "Parent";
  }

  @Override
  public JSONObject toJSON() throws JSONException {
    JSONObject ret = super.toJSON();
    try {
      ret.put(JSON.COLOR_STRING, color.toString());
    } catch (JSONException e) {
      Log.e("PhoneCase", "JSONException adding color");
    }
    return ret;
  }


  @Override
  public PhoneCase fromJSON(JSONObject jsonObject, Item parent) throws JSONException {
    String _color = jsonObject.getString(JSON.COLOR_STRING);
    PhoneCase ret = new PhoneCase((Phone) parent).color(_color);
    return ret;
  }
}
