package com.cuilinchen.mappart.foundit.Items;

import android.util.Log;

import com.cuilinchen.mappart.foundit.Questionnaire.ChoiceQuestion;
import com.cuilinchen.mappart.foundit.Questionnaire.StringQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by CUILINCHEN on 5/9/16.
 */
public class Dog extends Item {
  private Dog _this = this;

  public static final Dog dummy_dog = new Dog().kind("dummy").color("Brown").string("Dog");
  /*
  What type of dog do you have?

  What color is the fur?

  Any accessories on the dog? Name one or say no.
  */

  private String kind;
  private String string;

  public Dog kind(String _kind) {
    kind = _kind;
    return _this;
  }

  @Override
  public Dog color(String _fur_color) {
    return (Dog) super.color(_fur_color);
  }

  @Override
  public Item copyOf(boolean copy_attributes) {
    Dog ret = new Dog().kind("Akita").color("Brown").string("Dog");
    if(copy_attributes)
      ret.kind(kind).color(color).string("Dog");

    for(ItemAddOn<Dog> addOn: addon_list)
      ret.add(addOn);
    return ret;
  }

  public Dog string(String _string) {
    string = _string;
    return _this;
  }

  public Dog() {
    init_questionnaire();
  }


  @Override
  public String type() {
    return "Dog";
  }

  private static ArrayList<String> dog_kinds = new ArrayList<>(Arrays.asList(
      "Akita",
      "Black Alaskan Malamute",
      "Breaded Collie",
      "Boston Terrier",
      "Belgian Malinois",
      "Bullmastiff",
      "Dachsund",
      "English Setter"
  ));

  @Override
  public synchronized String generateRandomDetail() {
    if(dog_kinds.size() == 0)
      return "";
    String s = dog_kinds.get(new Random().nextInt(dog_kinds.size()));
    return s;
  }

  public void hasAccessories(boolean b){

  }

  @Override
  public void init_questionnaire() {
    try {
      questionnaire.put(new StringQuestion("What kind of dog is missing?", JSON.KIND_STRING),
          Dog.class.getMethod("kind", String.class));
      questionnaire.put(new StringQuestion("What color is the fur?", JSON.FURCOLOR_STRING),
          Dog.class.getMethod("color", String.class));
      questionnaire.put(new ChoiceQuestion<Boolean>("Any accessories on the dog?", Item.JSON.ADDONS_STRING)
              .putChoice(Boolean.TRUE)
              .putChoice(Boolean.FALSE),
          Item.class.getMethod("hasAccessories", boolean.class));
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }


  @Override
  public String toString() {
    return string;
  }

////////////////////////////////////////////////////////////////////////////////
  //    JSON
  //
  public class JSON {
    public static final String KIND_STRING = "Kind";
    public static final String FURCOLOR_STRING = "FurColor";
    public static final String TOSTRING_STRING = "toString";
  }

  @Override
  public JSONObject toJSON() throws JSONException {
    JSONObject ret = super.toJSON();
    try {
      ret.put(JSON.TOSTRING_STRING, string);
    } catch (JSONException e) {
      Log.e("PHONE", "JSON Exception putting toString string");
    }
    try {
      ret.put(JSON.KIND_STRING, kind);
    } catch (JSONException e) {
      Log.e("PHONE", "JSON Exception putting brand string");
    }
    try {
      ret.put(JSON.FURCOLOR_STRING, color);
    } catch (JSONException e) {
      Log.e("PHONE", "JSON Exception putting color string");
    }

    return ret;
  }


  @Override
  public Dog fromJSON(JSONObject jsonObject) throws JSONException {
    Dog d = new Dog()
        .kind(jsonObject.getString(Dog.JSON.KIND_STRING))
        .color(jsonObject.getString(Dog.JSON.FURCOLOR_STRING))
        .string(jsonObject.getString(Dog.JSON.TOSTRING_STRING));
    JSONArray addOnArray = jsonObject.getJSONArray(Item.JSON.ADDONS_STRING);
    for (int i = 0; i < addOnArray.length(); i++) {
      JSONObject jsonAddOn = (JSONObject) addOnArray.get(i);
      String type = jsonAddOn.getString(Item.JSON.TYPE_STRING);
      Method fromJSON = null;
      try {
        fromJSON = Class.forName(type).getMethod("fromJSON");
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      try {
        d.add((ItemAddOn<Phone>) fromJSON.invoke(Class.forName(type).newInstance(), jsonAddOn));
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return d;
  }
}
