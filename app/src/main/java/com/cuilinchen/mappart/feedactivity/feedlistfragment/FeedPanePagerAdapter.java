package com.cuilinchen.mappart.feedactivity.feedlistfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cuilinchen.mappart.NetworkManager;
import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.FeedActivity;

/**
 * Created by Art on 5/6/16.
 */
public class FeedPanePagerAdapter extends FragmentPagerAdapter{
  private Activity activity;

  public FeedPanePagerAdapter(FragmentManager fm, Activity _activity) {
    super(fm);
    activity = _activity;
  }

  @Override
  public int getCount() {
    return 2;
  }

  @Override
  public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  @Override
  public Fragment getItem(int position) {
    Fragment fragment = null;
    //bind fragment with service!
    Bundle args = new Bundle();

    System.out.printf("pager position: %d\n", position);

    switch (position){
      case 0:
        fragment = FeedlistFragment.newInstance(activity.getString(R.string.foundfeedlistlabel));
        break;
      case 1:
        fragment = FeedlistFragment.newInstance(activity.getString(R.string.lostfeedlistlabel));
        break;
    }
    return fragment;
  }
}
