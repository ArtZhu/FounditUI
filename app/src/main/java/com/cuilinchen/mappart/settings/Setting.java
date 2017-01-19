package com.cuilinchen.mappart.settings;

import android.app.Activity;
import android.view.View;

import java.io.Serializable;

/**
 * Created by CUILINCHEN on 5/5/16.
 */
public interface Setting extends Serializable{
  SettingPrompt getPrompt();
  View getView(Activity activity);
}
