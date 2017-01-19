package com.cuilinchen.mappart.foundit.Items;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.cuilinchen.mappart.Database.UserDBManager;
import com.cuilinchen.mappart.NetworkManager;
import com.cuilinchen.mappart.LoadingScreenActivity;
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
 * Created by CUILINCHEN on 5/1/2016.
 */
public class Phone extends Item {
  private Phone _this = this;

  public Phone(){
    init_questionnaire();
  }

  public static final Phone dummy_phone = new Phone().brand("dummy").color("Black").string("Phone");

  private String string;
  private String brand;
  private boolean hasCase;

  public Phone string(String _string) {
    string = _string;
    return _this;
  }

  public Phone brand(String _brand) {
    brand = _brand;
    return _this;
  }

  @Override
  public Phone color(String _color) {
    return (Phone) super.color(_color);
  }

  public Phone hasCase(Boolean _hasCase){
    hasCase = _hasCase;
    return _this;
  }

  @Override
  public Phone add(ItemAddOn... addOns) {
    for(ItemAddOn addOn: addOns)
      if(addOn instanceof PhoneCase)
        hasCase(true);
    return (Phone) super.add(addOns);
  }

  @Override
  public Phone remove(ItemAddOn... addOns) {
    for(ItemAddOn addOn: addOns)
      if(addOn instanceof PhoneCase)
        hasCase(false);
    return (Phone) super.remove(addOns);
  }

  @Override
  public String getColor() {
    return color;
  }
  public String type() {
    return "Phone";
  }

  private static void setPhoneBrands(ArrayList<String> strings){
    phone_brands = strings;
  }

  @Override
  public synchronized String generateRandomDetail() {
    if(random_details.isEmpty()) {
      random_details.addAll(used_details);
      used_details.clear();
    }

    Random random = new Random();
    int r;
    r = random.nextInt(random_details.size());
    String s = random_details.remove(r);
    used_details.add(s);
    if(phone_brands.size() > 0) {
      r = random.nextInt(phone_brands.size());
      return phone_brands.get(r) + ":" + s;
    }else{
      return "IPhone, " + s;
    }
  }

  @Override
  public void init_questionnaire() {

    try {
      questionnaire.put(new StringQuestion("What is the color?", JSON.COLOR_STRING),
          Phone.class.getMethod("color", String.class));
      questionnaire.put(new ChoiceQuestion<Boolean>("Does it have a case?", Item.JSON.ADDONS_STRING).
              putChoice(true).
              putChoice(false),
          Phone.class.getMethod("hasCase", Boolean.class)
      );
      questionnaire.put(new StringQuestion("What is the brand?", JSON.BRAND_STRING),
          Phone.class.getMethod("string", String.class));
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

  }

  @Override
  public Phone copyOf(boolean copy_attributes) {
    Phone ret = new Phone().brand("Apple").color("Black").string("Phone");//(Phone) super.copyOf(copy_attributes);
    if(copy_attributes){
      ret.brand(brand).color(color).string(string);
    }

    for(ItemAddOn<Phone> addOn: addon_list)
      ret.add(addOn);

    return ret;
  }

  public String toString(){
    return string;
  }

  private ArrayList<String> random_details = new ArrayList<>(Arrays.asList(
      "Lock Screen Picture is a Doggy",
      "The phone case is a cardholder",
      "naked phone crashed screen",
      "antiglare screen protector",
      "Phone case is a monet painting",
      "Headphones attached"
  ));
  private ArrayList<String> used_details = new ArrayList<>();

  private static ArrayList<String> phone_brands = new ArrayList<>();

////////////////////////////////////////////////////////////////////////////////
  //    JSON
  //
  public class JSON {
    public static final String COLOR_STRING = "Color";
    public static final String BRAND_STRING = "Brand";
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
      ret.put(JSON.BRAND_STRING, brand);
    } catch (JSONException e) {
      Log.e("PHONE", "JSON Exception putting brand string");
    }
    try {
      ret.put(JSON.COLOR_STRING, color);
    } catch (JSONException e) {
      Log.e("PHONE", "JSON Exception putting color string");
    }

    return ret;
  }

  @Override
  public Phone fromJSON(JSONObject jsonObject) throws JSONException {
    Phone p = new Phone()
        .brand(jsonObject.getString(Phone.JSON.BRAND_STRING))
        .color(jsonObject.getString(Phone.JSON.COLOR_STRING))
        .string(jsonObject.getString(Phone.JSON.TOSTRING_STRING));
    JSONArray addOnArray = jsonObject.getJSONArray(Item.JSON.ADDONS_STRING);
    for(int i=0; i<addOnArray.length(); i++) {
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
        p.add((ItemAddOn<Phone>) fromJSON.invoke(Class.forName(type).newInstance(), jsonAddOn));
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
    return p;
  }


  public static class InitPhoneBrands extends AsyncTask<LoadingScreenActivity, Integer, ArrayList<String>>{
    private boolean init = false;
    private LoadingScreenActivity loadingScreenActivity;
    @Override
    protected ArrayList<String> doInBackground(LoadingScreenActivity[] activities){
      if(!init){
        loadingScreenActivity = activities[0];
        SQLiteDatabase db = loadingScreenActivity.openOrCreateDatabase(UserDBManager.PhoneDBName, Context.MODE_PRIVATE, null);

        ArrayList<String> ret = NetworkManager.retrieveInformationAbout(Phone.dummy_phone);


        return ret;
      }
      return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
      if(!init) {
        setPhoneBrands(strings);

        loadingScreenActivity.goNext();

        init = true;
      }
    }
  }
}
