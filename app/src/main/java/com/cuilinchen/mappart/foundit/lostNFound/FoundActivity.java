package com.cuilinchen.mappart.foundit.lostNFound;

import android.support.v7.app.AppCompatActivity;

import com.cuilinchen.mappart.feedactivity.model.Feed;

/**
 * Created by CUILINCHEN on 5/1/2016.
 */
public class FoundActivity extends FindFoundActivitySkeleton{

  public FoundActivity(){
    super();
    category = Feed.CATEGORY_FOUND;
  }
}
