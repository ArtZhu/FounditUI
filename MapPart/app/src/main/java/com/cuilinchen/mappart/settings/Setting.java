package com.cuilinchen.mappart.settings;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.io.Serializable;

/**
 * Created by Art on 5/5/16.
 */
public interface Setting extends Serializable{
  public SettingPrompt getPrompt();
  public View getView(Activity activity);
}
