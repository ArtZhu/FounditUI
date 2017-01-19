package com.cuilinchen.mappart.foundit.Items;

import com.cuilinchen.mappart.foundit.Questionnaire.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Art on 5/7/16.
 */
public abstract class ItemAddOn<T> extends Item {
  protected T parent;

  public ItemAddOn(T _parent){
    parent = _parent;
  }

  @Override
  public List<Question> questionnaire() {
    return new ArrayList<>();
  }

  @Override
  public abstract ItemAddOn<T> copyOf(boolean copy_attributes);

  public abstract ItemAddOn<T> fromJSON(JSONObject jsonObject, Item parent) throws JSONException;
  @Override
  public Item fromJSON(JSONObject jsonObject) throws JSONException {
    throw new UnsupportedOperationException();
  }
}
