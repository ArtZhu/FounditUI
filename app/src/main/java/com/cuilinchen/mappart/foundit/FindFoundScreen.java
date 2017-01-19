package com.cuilinchen.mappart.foundit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.foundit.lostNFound.FindActivity;
import com.cuilinchen.mappart.foundit.lostNFound.FoundActivity;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;

public class FindFoundScreen extends Fragment {
  private FindFoundScreen _this = this;

  private TextView find_half, found_half;

  public FindFoundScreen() {
  }

  public static FindFoundScreen newInstance() {
    return new FindFoundScreen();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.activity_find_found_screen, null, false);


    find_half = (TextView) v.findViewById(R.id.find_half);
    found_half = (TextView) v.findViewById(R.id.found_half);

    find_half.setTypeface(TypefaceFactory.font("Futura-Std-Light_19054.ttf", _this.getActivity()));
    found_half.setTypeface(TypefaceFactory.font("Futura-Std-Light_19054.ttf", _this.getActivity()));

    find_half.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        System.out.println(0);
        Intent i = new Intent(getActivity(), FindActivity.class);
        startActivity(i);
      }
    });

    found_half.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(getActivity(), FoundActivity.class);
        startActivity(i);
      }
    });

    return v;
  }
}
