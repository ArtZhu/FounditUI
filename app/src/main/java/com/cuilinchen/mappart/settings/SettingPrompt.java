package com.cuilinchen.mappart.settings;

import android.app.Activity;
import android.view.View;

import java.io.Serializable;

/**
 * Created by CUILINCHEN on 5/5/16.
 */
public interface SettingPrompt extends Serializable {
  int TEXT_CONTENT = View.generateViewId();
  int TEXT_TITLE = View.generateViewId();
  int IMAGE_CONTENT = View.generateViewId();

  View getView(Activity activity);
}

