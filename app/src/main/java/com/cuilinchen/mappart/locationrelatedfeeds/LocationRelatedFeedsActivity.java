package com.cuilinchen.mappart.locationrelatedfeeds;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cuilinchen.mappart.Database.SettingDBManager;
import com.cuilinchen.mappart.FeedDetailActivity.DetailActivity;
import com.cuilinchen.mappart.NetworkManager;
import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.foundit.lostNFound.FindActivity;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

public class LocationRelatedFeedsActivity extends AppCompatActivity implements RecyclerFeedlistFragment.OnFeedSelected{
  private LocationRelatedFeedsActivity _this = this;

  private ArrayList<Feed> feeds = new ArrayList<>();
  private ProgressBar progressBar;
  private ImageView button_back;

  private LatLng latlng;

  private Item currentSectionItem;
  private int currentSectionCategory;

  private RecyclerFeedlistFragment nested_fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    configureTheme();
    setContentView(R.layout.activity_location_related_feeds);
    CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    progressBar.setVisibility(View.INVISIBLE);

    button_back = (ImageView) findViewById(R.id.imgbutton_back);
    button_back.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        }
    );


    Address addr = null;
    try {
      Intent i = getIntent();
      Bundle b = i.getExtras();
      Double lat = b.getDouble(Feed.JSON.LAT_STRING);
      Double lng = b.getDouble(Feed.JSON.LNG_STRING);
      addr = (Address) b.getParcelable(Feed.ADDRESS_STRING);
      currentSectionCategory = (int) b.getInt(FindActivity.CATEGORY_ARG);
      currentSectionItem = (Item) b.getSerializable(FindActivity.ITEM_ARG);
      latlng = new LatLng(lat, lng);
    }catch(NullPointerException e){
      latlng = getCurrentLatLng();
    }

    String postalcode = "";
    try {
      postalcode = addr.getPostalCode();
    }catch (NullPointerException e){
    }

    String title_string = "";
    if(postalcode != null) {
      title_string = String.format("Founds near %s", postalcode);
      toolbarLayout.setTitle(title_string);
    }

    for (int i = 0; i < toolbarLayout.getChildCount(); i++) {
      View v = toolbar.getChildAt(i);
      if (v != null && v instanceof TextView) {
        TextView t = (TextView) v;
        CharSequence title = t.getText();
        System.out.print(1);
        TypefaceFactory.format(t, "Futura Heavy font.ttf", _this);
        t.setTextSize(30);
      }
    }

    nested_fragment = RecyclerFeedlistFragment.newInstance();

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          //.add(R.id.nested_scroll_root, nested_fragment, "RecyclerFeedFragment")
          //.replace(R.id.recycler_list, nested_fragment, "RecyclerFeedFragment")
          .setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_bottom, R.animator.pop_enter, R.animator.pop_exit)
          .add(R.id.scroll_root, nested_fragment, "RecyclerFeedFragment")
          .commit();
    }
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    System.out.println("POST CREATED");


    AsyncTask<LatLng, Integer, ArrayList<Feed>> task = new LocationFeedRetriver();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
      task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, latlng);
    else
      task.execute(latlng);
  }

  @Override
  public void onFeedSelected(Feed feed) {
    //
    Intent i = new Intent(_this, DetailActivity.class);
    i.putExtra(DetailActivity.FEED_ARG, feed);
    startActivity(i);
  }

  @Override
  public ArrayList<Feed> getFeeds() {
    System.out.println("GET FEEDS GETS CALLED !#(@I)!*@!#()@!"); return feeds;
  }

  class LocationFeedRetriver extends AsyncTask<LatLng, Integer, ArrayList<Feed>>{

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      progressBar.setVisibility(View.VISIBLE);
      System.out.println("PreExecute!!!!!!!!!!!!");
    }

    @Override
    protected ArrayList<Feed> doInBackground(LatLng... params) {
      LatLng _latlng = (LatLng) params[0];

      System.out.println("Execute Latlng: _latlng!!!!!!!!!!!!");

      publishProgress(10);

      try {
        Thread.sleep(100);
      }catch (InterruptedException e){

      }

      publishProgress(33);

      try {
        Thread.sleep(100);
      }catch (InterruptedException e){

      }

      publishProgress(66);

      try {
        Thread.sleep(100);
      }catch (InterruptedException e){

      }


      System.out.println("item null: " + currentSectionItem == null);
      ArrayList<Feed> ret = NetworkManager.requestFeedForLocation(_this, 10, currentSectionCategory, currentSectionItem, _latlng);
      System.out.println("RETRIEVED FEEEDS:" + ret);

      return ret;
    }

    @Override
    protected void onPostExecute(ArrayList<Feed> feeds) {
      super.onPostExecute(feeds);

      Collections.sort(feeds);

      System.out.println("POSTExecute!!!!!!!!!!!!");

      setFeeds(feeds);

      progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      progressBar.setProgress(values[0]);
    }
  }

  private void setFeeds(ArrayList<Feed> _feeds){
    feeds = _feeds;

    System.out.println("Setting feeds: " + feeds);

    nested_fragment.update();
    //notify feeds changed
  }

  private LocationManager locationManager = null;
  private LatLng getCurrentLatLng(){
    if(locationManager == null)
      locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    return new LatLng(loc.getLatitude(), loc.getLongitude());
  }


  private void configureTheme(){

    SettingDBManager.createTable(getApplicationContext());
    int resId = R.style.gray_style;
    for(String s: SettingDBManager.retrieveList()) {
      int i=0;
      if ((i=s.indexOf("THEME:")) >= 0) {
        s = s.substring(s.indexOf(":") + 1);
        switch (s){
          case "Blue":
            resId = R.style.blue_style;
            break;

          case "Pink":
            resId = R.style.pink_style;
            break;

          case "Gray":

            break;
        }
        break;
      }
    }
    setTheme(resId);
  }
}
