package com.cuilinchen.mappart.settings;

import android.app.Activity;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Art on 5/5/16.
 */
public abstract class Choice implements Serializable{
  public static final int TEXT_CONTENT = View.generateViewId();
  public static final int TEXT_TITLE = View.generateViewId();
  public static final int IMAGE_CONTENT = View.generateViewId();
  /****************************
   *
   * @param activity
   * @return
   *
   * Choice view textContent must be named TEXT_CONTENT
   * Choice view textTitle must be named TEXT_TITLE
   *
   */
  public abstract View getView(Activity activity);

  protected ArrayList<OnClickListener> listeners;
  public Choice(){
    listeners = new ArrayList<>();
  }
  public Choice addChoiceOnClickListener(OnClickListener listener){
    listeners.add(listener);
    return this;
  }

  public Choice removeChoiceOnClickListener(OnClickListener listener){
    listeners.remove(listener);
    return this;
  }

  public void onClick(){
    for(OnClickListener listener: listeners)
      listener.choiceClicked(this);
  }

  /*****************
   *
   */
  public interface OnClickListener extends Serializable{
    void choiceClicked(Choice c);
  }
}