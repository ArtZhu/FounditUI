package com.cuilinchen.mappart.foundit.lostNFound.adapters;

import android.app.Activity;
import android.graphics.Outline;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.viewFactory.QuestionFactory;

import java.util.List;

/**
 * Created by Art on 5/7/16.
 */

public class ChoicesAdapter extends ArrayAdapter {
  private Activity activity;
  private int resource;
  private List objects;
  private LayoutInflater inf;

  public ChoicesAdapter(Activity _activity, int _resource, List _objects) {
    super(_activity, R.layout.choiceslot_string, R.id.choiceslot_textview, _objects);

    activity = _activity;
    resource = _resource;
    objects = _objects;
    inf = activity.getLayoutInflater();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View v = inf.inflate(R.layout.choiceslot_string, parent, false);

    TextView tv = (TextView) v.findViewById(R.id.choiceslot_textview);
    Object o = objects.get(position);
    if(o.equals(Boolean.TRUE))
      tv.setText("Yes");
    else if(o.equals(Boolean.FALSE))
      tv.setText("No");
    else
      tv.setText(o.toString());

    /*
    float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, activity.getResources().getDisplayMetrics());
    tv.setTextSize(pixels);
*/

    return QuestionFactory.question(v, activity);
  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    View v = inf.inflate(R.layout.choiceslot_string, parent, false);

    TextView tv = (TextView) v.findViewById(R.id.choiceslot_textview);
    Object o = objects.get(position);
    if(o.equals(Boolean.TRUE))
      tv.setText("Yes");
    else if(o.equals(Boolean.FALSE))
      tv.setText("No");
    else
      tv.setText(o.toString());

    //?
    v.setPadding(15, 15, 0, 15);
    v.setOutlineProvider(new ViewOutlineProvider() {
      @Override
      public void getOutline(View view, Outline outline) {

      }
    });

    return QuestionFactory.question(v, activity);
  }
}