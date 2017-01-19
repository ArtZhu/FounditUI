package com.cuilinchen.mappart.settings.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cuilinchen.mappart.settings.FlowSetting;

/**
 * Created by Art on 5/5/16.
 */
public class SettingFragment extends Fragment {
  private SettingFragment _this = this;
  private FlowSetting flowSetting;

  public SettingFragment() { }

  public static SettingFragment newInstance(
                          FlowSetting _flowSetting
                          )
  {
    SettingFragment ret = new SettingFragment();
    final Bundle args = new Bundle();
    args.putSerializable(ARG_FLOWSETTING, _flowSetting);

    ret.setArguments(args);
    return ret;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Bundle args = getArguments();
    flowSetting = (FlowSetting) args.get(ARG_FLOWSETTING);

    Activity associated_activity = getActivity();

    return flowSetting.getView(associated_activity);
  }

  private static final String ARG_FLOWSETTING = "fs";
}
