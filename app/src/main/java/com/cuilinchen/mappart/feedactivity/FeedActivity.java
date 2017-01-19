package com.cuilinchen.mappart.feedactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cuilinchen.mappart.Database.SettingDBManager;
import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.feedlistfragment.FeedPaneFragment;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.foundit.lostNFound.FindActivity;
import com.cuilinchen.mappart.locationrelatedfeeds.LocationRelatedFeedsActivity;
import com.cuilinchen.mappart.services.FeedRetrieverService;
import com.cuilinchen.mappart.viewFactory.SnackbarFactory;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Locale;

public class FeedActivity extends AppCompatActivity implements OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener
    , FloatingActionButton.OnClickListener
    , PlaceSelectionListener {
  private FeedActivity _this = this;
  private FrameLayout root_view;

  private GoogleMap mMap;
  private View locationButton, mapView;
  private SupportMapFragment mapFragment;

  private LocationManager location_manager;
  private LocationListener location_listener;

  private FeedPaneFragment feedPaneFragment;
  private static final String FeedPaneFragment_ARG = "?AFKDJSL";
  private View feedPaneFragmentView;

  private PlaceAutocompleteFragment autocompleteFragment;

  private FloatingActionButton fab;

  private EditText search_box;
  private Geocoder geocoder;

  private Item currentSectionItem;
  private int currentSectionCategory;

  private StartLocationRelatedFeedAcitivityTask currentTask = null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //configureTheme();
    setContentView(R.layout.activity_feed);

    root_view = (FrameLayout) findViewById(R.id.feed_root);

    location_manager = (LocationManager) getSystemService(LOCATION_SERVICE);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    fab = (FloatingActionButton) findViewById(R.id.map_fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        disableMap();
      }
    });
    fab.setVisibility(View.GONE);

// Retrieve the PlaceAutocompleteFragment.
    autocompleteFragment = (PlaceAutocompleteFragment)
        getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

    // Register a listener to receive callbacks when a place has been selected or an error has
    // occurred.
    autocompleteFragment.setOnPlaceSelectedListener(this);

    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

    Intent i = getIntent();
    Bundle args = i.getExtras();
    currentSectionCategory = (Integer) args.get(FindActivity.CATEGORY_ARG);
    currentSectionItem = (Item) args.get(FindActivity.ITEM_ARG);
    System.out.println("currentSectionItem " + currentSectionItem == null);
    // Retrieve the TextViews that will display details about the selected place.
    System.out.println("W eare here");
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    this.getFeedPaneAsync();

  }

  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    //mMap.setMyLocationEnabled(false);
    mMap.setMyLocationEnabled(true);
    mMap.setOnMyLocationButtonClickListener(_this);

    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
      @Override
      public void onMapLongClick(LatLng latLng) {


        startLocationRelatedActivity(latLng);
        //new StartRelationRelatedFeedAcitivityTask().execute(latLng);
      }
    });

    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
      @Override
      public void onMapClick(LatLng latLng) {

        enableMap();
      }
    });

    initLocationButton();
    onMyLocationButtonClick();


  }

  private void startLocationRelatedActivity(LatLng latLng){
    if(currentTask == null) {
      currentTask = new StartLocationRelatedFeedAcitivityTask();

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        currentTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, latLng);
      else
        currentTask.execute(latLng);
    }
  }

  public static final String FEED_DATABASE_NAME = "feed.db";

  @Override
  public boolean onMyLocationButtonClick() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return false;
    }
    Location location = location_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    mMap.moveCamera(CameraUpdateFactory.newLatLng(
        new LatLng(location.getLatitude(), location.getLongitude())));

    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    return true;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      //case R.id.search_box:
      //onSearchRequested();
      //break;
      default:
        onMyLocationButtonClick();
        break;
    }
  }



  /**
   * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
   */


  @Override
  public void onPlaceSelected(Place place) {
    Log.i(TAG, "Place Selected: " + place.getName());

    LatLng selected = place.getLatLng();

    startLocationRelatedActivity(selected);

  }

  /**
   * Callback invoked when PlaceAutocompleteFragment encounters an error.
   */
  private static String TAG = "tag";

  @Override
  public void onError(Status status) {
    Log.e(TAG, "onError: Status = " + status.toString());

    Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
        Toast.LENGTH_SHORT).show();
  }

  /**
   * Helper method to format information about a place nicely.
   */
  private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                            CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
    Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
        websiteUri));
    return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
        websiteUri));

  }

  public void moveCamera(LatLng latLng){
    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
  }

  @Override
  protected void onResume() {
    super.onResume();

    recoverFeedPane();
  }

  @Override
  protected void onResumeFragments() {
    super.onResumeFragments();

    recoverFeedPane();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //      Init
  //
  private void initLocationButton() {
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
        findFragmentById(R.id.map);
    mapView = mapFragment.getView();

    locationButton = null;
    if (mapView != null && mapView.findViewById(1) != null) {
      locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);
    }
    // and next place it, on bottom right (as Google Maps app)
    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
        locationButton.getLayoutParams();
    // position on right bottom
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
    layoutParams.setMargins(0, 0, 30, 30);
  }

  private void getFeedPaneAsync() {
    new FeedPaneCreationTask().execute((FeedRetrieverService) null);
  }



  class FeedPaneCreationTask extends AsyncTask<FeedRetrieverService, Void, Void> {
    @Override
    protected Void doInBackground(FeedRetrieverService... params) {
      feedPaneFragment = FeedPaneFragment.newInstance(null);
      feedPaneFragmentView = feedPaneFragment.getView();
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.feed_root, feedPaneFragment, "feedpane")
          .commitAllowingStateLoss();

      //getSupportFragmentManager().putFragment(null, FeedPaneFragment_ARG, feedPaneFragment);

      //root_view.bringToFront();
    }
  }

  class StartLocationRelatedFeedAcitivityTask extends AsyncTask<LatLng, Object, Bundle>{
    private StartLocationRelatedFeedAcitivityTask task = this;
    Snackbar s;

    @Override
    protected void onPreExecute() {
      super.onPreExecute();

      s = SnackbarFactory.submissionSnackbar(_this, mapView, "Searching for finds", Snackbar.LENGTH_INDEFINITE,
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              task.cancel(true);
            }
          });
    }

    @Override
    protected Bundle doInBackground(LatLng... params) {
      s.show();

      LatLng selected = params[0];
      Address addr = null;
      Bundle b = new Bundle();
      try {
        addr = geocoder.getFromLocation(selected.latitude, selected.longitude, 10).get(0);
      } catch (IOException e) {
        e.printStackTrace();
      }

      b.putDouble(Feed.JSON.LAT_STRING, selected.latitude);
      b.putDouble(Feed.JSON.LNG_STRING, selected.longitude);
      b.putParcelable(Feed.ADDRESS_STRING, addr);
      b.putInt(FindActivity.CATEGORY_ARG, currentSectionCategory);
      if(currentSectionCategory == Feed.CATEGORY_LOST)
        b.putSerializable(FindActivity.ITEM_ARG, currentSectionItem);
      return b;
    }

    @Override
    protected void onPostExecute(Bundle bundle) {
      super.onPostExecute(bundle);

      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      currentTask = null;

      Intent i = new Intent(_this, LocationRelatedFeedsActivity.class);
      i.putExtras(bundle);

      s.dismiss();

      startActivity(i);
    }

    @Override
    protected void onCancelled(Bundle bundle) {
      super.onCancelled(bundle);
      s.dismiss();
    }

    private void startLocationRelatedFeeds(LatLng selected){

    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    super.onSaveInstanceState(outState);
  }

  private boolean mapEnabled = false;
  public void disableMap() {
    //mapView.setClickable(false);
    //mapView.setFocusable(false);
    ///mapView.setLongClickable(false);
    if(mapEnabled) {
      recoverFeedPane();

      final FragmentManager fm = getSupportFragmentManager();
      final FragmentTransaction ft = fm.beginTransaction();
      ft
          .setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_bottom,
              R.animator.enter_from_bottom, R.animator.exit_to_bottom)//, R.animator.pop_enter, R.animator.pop_exit)
          .attach(feedPaneFragment)
          .commit();

      //fab.setVisibility(View.INVISIBLE);
      fadeOut(fab);
      mapEnabled = false;
    }
  }

  public void enableMap() {
    //mapView.setClickable(true);
    //mapView.setFocusable(true);
    //mapView.setLongClickable(true);
    if(!mapEnabled) {
      recoverFeedPane();

      final FragmentManager fm = getSupportFragmentManager();
      final FragmentTransaction ft = fm.beginTransaction();
      ft
          //.setCustomAnimations(android.R.anim.cycle_interpolator, android.R.anim.linear_interpolator)
          .setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_bottom,
              R.animator.enter_from_bottom, R.animator.exit_to_bottom)//, R.animator.pop_enter, R.animator.pop_exit)
          .detach(feedPaneFragment)
          .commit();

      //fab.setVisibility(View.VISIBLE);
      fadeIn(fab);
      mapEnabled = true;
    }
  }

  private void recoverFeedPane(){
    /*
    if (feedPaneFragmentView == null) {
      //if (feedPaneFragment == null)
      //feedPaneFragment = (FeedPaneFragment) getSupportFragmentManager().getFragment(null, FeedPaneFragment_ARG);
      if(feedPaneFragment == null)
        getFeedPaneAsync();
      else {
        feedPaneFragmentView = feedPaneFragment.getView();
      }
    }
    */
  }

  private void fadeOut(final View v) {
    Animation fadeOut = new AlphaAnimation(1, 0);
    fadeOut.setInterpolator(new AccelerateInterpolator());
    fadeOut.setDuration(300);

    fadeOut.setAnimationListener(new Animation.AnimationListener() {
      public void onAnimationEnd(Animation animation) {
        v.setVisibility(View.GONE);
      }

      public void onAnimationRepeat(Animation animation) {
      }

      public void onAnimationStart(Animation animation) {
      }
    });

    v.startAnimation(fadeOut);
  }

  private void fadeIn(final View v) {
    Animation fadeIn = new AlphaAnimation(0, 1);
    fadeIn.setInterpolator(new AccelerateInterpolator());
    fadeIn.setDuration(1000);

    fadeIn.setAnimationListener(new Animation.AnimationListener() {
      public void onAnimationEnd(Animation animation) {
        v.setVisibility(View.VISIBLE);
      }

      public void onAnimationRepeat(Animation animation) {
      }

      public void onAnimationStart(Animation animation) {
      }
    });

    v.startAnimation(fadeIn);
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
