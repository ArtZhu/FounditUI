package com.cuilinchen.mappart.foundit.lostNFound.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.viewFactory.DropDownViewFactory;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;

import java.util.List;

/**
 * Created by Art on 5/7/16.
 */

public class ItemTypeAdapter extends ArrayAdapter<Item> {
  private List<Item> items;
  private Activity activity;
  private int resource;
  private LayoutInflater inf;

  public ItemTypeAdapter(Activity _activity, int _resource, List<Item> _items) {
    super(_activity, _resource, R.id.itemtype_textview, _items);

    activity = _activity;
    resource = _resource;
    items = _items;
    inf = activity.getLayoutInflater();

    //this.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    View v = inf.inflate(R.layout.itemtypeslot_string, null);
    TextView tv = (TextView) v.findViewById(R.id.itemtype_textview);
    tv.setText(items.get(position).type());

    TypefaceFactory.format(tv, "Futura-Std-Light_19054.ttf", activity);

    return DropDownViewFactory.dropDownView(v, activity);
  }

}
