package com.cuilinchen.mappart.foundit.Items;

import android.util.Log;

import com.cuilinchen.mappart.foundit.Questionnaire.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Art on 5/1/2016.
 */
public abstract class Item implements Serializable{
  protected final Item _this = this;
  protected transient Map<Question, Method> questionnaire;

  protected String color;

  protected List<ItemAddOn> addon_list;

  public abstract String type();
  public abstract String generateRandomDetail();
  //public abstract String getDetail();
  public abstract void init_questionnaire();

  public Item() {
    addon_list = new ArrayList<>();
    questionnaire = new HashMap<Question, Method>();
  }

  public Item color(String _color){
    color = _color;
    return _this;
  }

  public String getColor(){
    return color;
  }

  public void setAnswer(Question q, Object o){
    try {
      questionnaire.get(q).invoke(_this, o);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public List<ItemAddOn> getAddon_list(){
    return addon_list;
  }

  public Item add(ItemAddOn... addOns) {

    for (ItemAddOn addOn : addOns)
      addon_list.add(addOn);

    return _this;
  }

  public Item remove(ItemAddOn... addOns) {
    for (ItemAddOn addOn : addOns)
      addon_list.remove(addOn);
    return _this;
  }

  public boolean hasAccessories(){
    return !addon_list.isEmpty();
  }

  public List<Question> questionnaire() {
    if(questionnaire == null || questionnaire.isEmpty()){
      init_questionnaire();
    }
    List<Question> ret = new ArrayList<>();

    for(Map.Entry<Question, Method> entry: questionnaire.entrySet())
      ret.add(entry.getKey());

    return ret;
  }

  public abstract Item copyOf(boolean copy_attributes);
  /*
  public Item copyOf(boolean copy_attributes){
    Item copy = null;
    try {
      //copy = (Item) Item.class.getConstructors()[0].newInstance();
      copy = ((Item) Item.class.getClassLoader().loadClass(_this.getClass().toString()).newInstance());
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    //} catch (InvocationTargetException e) {
      //e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    if(copy_attributes) {
      for (ItemAddOn addOn : addon_list) {
        copy.add(addOn.copyOf(copy_attributes));
      }
    }
    return copy;
  }
  */



  public boolean isOfType(String type) {
    return type().equalsIgnoreCase(type);
  }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //    JSON
  //
  public class JSON{
    public static final String ADDONS_STRING = "AddOns";
    public static final String TYPE_STRING = "ITEM_TYPE!";
  }

  public JSONObject toJSON() throws JSONException {
    JSONObject ret = new JSONObject();

    JSONArray addon_array = new JSONArray();
    for (ItemAddOn addOn: getAddon_list())
      addon_array.put(addOn.toJSON());
    try {
      ret.put(JSON.ADDONS_STRING, addon_array);
    } catch (JSONException e) {
      Log.e("ITEM", "JSONException while adding JSONaddon");
    }

    ret.put(JSON.TYPE_STRING, type());
    return ret;
  }

  public abstract Item fromJSON(JSONObject jsonObject) throws JSONException;
}
