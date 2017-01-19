package com.cuilinchen.mappart.feedactivity.feedlistfragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cuilinchen.mappart.NetworkManager;
import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.foundit.Items.Dog;
import com.cuilinchen.mappart.foundit.Items.Phone;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Art on 5/6/16.
 */
public class FeedlistFragment extends Fragment {
  private final FeedlistFragment _this = this;
  private ListView lv;

  private String tag;

  //losts or founds
  private ArrayList<Feed> feeds = new ArrayList<>();
  //private HashMap<Feed, Item> feeds = new HashMap<>();

  public FeedlistFragment() {}

  public static FeedlistFragment newInstance(String tag){
    FeedlistFragment f = new FeedlistFragment();

    Bundle args = new Bundle();
    args.putString("TAG", tag);
    f.setArguments(args);
    return f;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tag = getArguments().getString("TAG");
    new NetworkRetrieveTask().execute(tag);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_feed_list, container, false);
    lv = (ListView) v.findViewById(R.id.listview_feed);

    TextView textView = (TextView) v.findViewById(R.id.text);

    LatLng NewYorkManhattan = new LatLng(40.758896, -73.985130);
    LatLng BostonQuincyMarket = new LatLng(42.3606500, -71.0545000);
    LatLng FlynnRecreationCenter = new LatLng(42.336667, -71.167202);

    System.out.println("tag=" + tag);
    if(tag.equals(getString(R.string.lostfeedlistlabel))){
      feeds = NetworkManager.requestFeedForLocation(getContext(), 6, Feed.CATEGORY_LOST, Dog.dummy_dog, NewYorkManhattan, BostonQuincyMarket, FlynnRecreationCenter);
          /*
      feeds.add(new SimpleFeed.Builder()
          .title("Lost Dog!")
          .detail("Did anyone see my yellow dog?")
          .id(1)
          .item(Dog.dummy_dog)
          .lat(40.714)
          .lng(-73.961)
          .timestamp(new Timestamp(1462813140397L))
          .build());

      feeds.add(new SimpleFeed.Builder()
          .title("Lost Dog!")
          .detail("My dog is blue")
          .id(2)
          .item(Dog.dummy_dog)
          .lat(40.714)
          .lng(-73.961)
          .timestamp(new Timestamp(1460013139397L))
          .build());
          */
    }else{
      feeds = NetworkManager.requestFeedForLocation(getContext(), 6, Feed.CATEGORY_FOUND, Phone.dummy_phone, NewYorkManhattan, BostonQuincyMarket, FlynnRecreationCenter);
      /*
      long l = Calendar.getInstance().getTime().getTime();
      feeds.add(new SimpleFeed.Builder()
          .title("Found iphone")
          .detail("Black 6s")
          .id(3)
          .item(Phone.dummy_phone)
          .lat(40.714)
          .lng(-73.961)
          .timestamp(new Timestamp(1462813139397L))
          .build());

      feeds.add(new SimpleFeed.Builder()
          .title("Found Samsung")
          .detail("Galaxy S6 edge plus with a white case")
          .id(4)
          .item(Phone.dummy_phone)
          .lat(40.714)
          .lng(-73.961)
          .timestamp(new Timestamp(l))
          .build());
          */
    }

    Collections.sort(feeds);

    lv.setAdapter(new FeedlistAdapter(getActivity(), R.layout.fragment_feed_list_slot, feeds));

    return v;
  }


  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

  private static final String addr = "136.167.109.216";
  private static final int lost_port = 8819;
  private static final int found_port = 8820;

  class NetworkRetrieveTask extends AsyncTask<String, Integer, Integer>{

    @Override
    protected Integer doInBackground(String... params) {
      String tag = params[0];

      int port;
      if(tag.equals(getString(R.string.lostfeedlistlabel)))
        port = lost_port;
      else
        port = found_port;

      try {
        JSONObject jsonObject = NetworkManager.getJSONfromIP(addr, port);

        try {
          feeds = NetworkManager.FeedlistJSON.toFeedList(jsonObject);
        } catch (JSONException e) {
          Log.e("FEEDLIST", "CREATION EXCEPTION");
        }

      }catch (NullPointerException e){
        Log.e("FreelistFragment", "JSON from " + addr + ":" + port + " failed");
      }

      return 0;
    }
  }
}
