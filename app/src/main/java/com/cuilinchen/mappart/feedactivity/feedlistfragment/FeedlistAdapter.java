package com.cuilinchen.mappart.feedactivity.feedlistfragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuilinchen.mappart.FeedDetailActivity.DetailActivity;
import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.FeedActivity;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.foundit.lostNFound.FindActivity;
import com.cuilinchen.mappart.viewFactory.FeedlistFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by CUILINCHEN on 5/6/16.
 */
public class FeedlistAdapter extends ArrayAdapter<Feed> {
  private final Activity activity;
  private final int resource;
  private final List<Feed> feeds;
  private final LayoutInflater inflater;

  private final int DEFAULT_ITEM_NUM = 5;

  public FeedlistAdapter(Activity _activity, int _resource, List<Feed> _feeds) {
    super(_activity, _resource, _feeds);

    activity = _activity;
    resource = _resource;
    feeds = _feeds;

    System.out.println("Creating feedlist adapter with " + feeds);

    inflater = activity.getLayoutInflater();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View v = inflater.inflate(R.layout.fragment_feed_list_slot, parent, false);
    ImageView img = (ImageView) v.findViewById(R.id.imageview_feed_list_slot);
    TextView tv_TITLE = (TextView) v.findViewById(R.id.textview_feed_list_slot_TITLE);
    TextView tv_DETAIL = (TextView) v.findViewById(R.id.textview_feed_list_slot_DETAIL);
    TextView tv_TIME = (TextView)  v.findViewById(R.id.textview_feed_list_slot_TIME);


    final Feed f = feeds.get(position);

    System.out.println("Title = " + f.getTitle());
    System.out.println("Detail = " + f.getDetail());
    System.out.println("Time = " + f.getTime());
    //img.set
    tv_TITLE.setText(f.getTitle());
    tv_DETAIL.setText(f.getDetail());
    tv_TIME.setText(f.getTime());

    v.findViewById(R.id.camera_mover).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ((FeedActivity) activity).moveCamera(new LatLng(f.lat, f.lng));
      }
    });

    v.findViewById(R.id.detail_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(activity, DetailActivity.class);
        i.putExtra(DetailActivity.FEED_ARG, f);
        activity.startActivity(i);
      }
    });

    return FeedlistFactory.slot(v, activity);
  }

  @Override
  public int getCount() {
    return feeds.size() < DEFAULT_ITEM_NUM ? feeds.size() : DEFAULT_ITEM_NUM;
  }
}
