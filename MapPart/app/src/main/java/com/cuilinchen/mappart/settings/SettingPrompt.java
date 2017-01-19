package com.cuilinchen.mappart.settings;

import android.app.Activity;
import android.view.View;

import java.io.Serializable;

/**
 * Created by Art on 5/5/16.
 */
public interface SettingPrompt extends Serializable {
  public static final int TEXT_CONTENT = View.generateViewId();
  public static final int TEXT_TITLE = View.generateViewId();
  public static final int IMAGE_CONTENT = View.generateViewId();

  public View getView(Activity activity);
}

