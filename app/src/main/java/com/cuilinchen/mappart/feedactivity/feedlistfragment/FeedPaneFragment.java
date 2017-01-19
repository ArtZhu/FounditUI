package com.cuilinchen.mappart.feedactivity.feedlistfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.FeedActivity;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.services.FeedRetrieverService;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;

import java.util.ArrayList;

/**
 * Created by Art on 5/6/16.
 */
public class FeedPaneFragment extends Fragment {
  private final FeedPaneFragment _this = this;

  private ViewPager pager;
  private TextSwitcher pager_label;

  private FeedRetrieverService feedRetrieverService;
  private ArrayList<Feed> losts;
  private ArrayList<Feed> founds;

  private FeedActivity fa;

  public FeedPaneFragment() {}

  public static FeedPaneFragment newInstance(FeedRetrieverService _feedRetriver){
    FeedPaneFragment f = new FeedPaneFragment();
    //feedRetrieverService = _feedRetriver;
    return f;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_feed_pane, container, false);
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    fa = (FeedActivity) getActivity();

    init_pager_label(view);
    init_pager(view);
    System.out.println("!!!");


    view.findViewById(R.id.map_enabler).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fa.enableMap();
      }
    });

    System.out.println("!!!--");

    fa.disableMap();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    pager.setVisibility(View.VISIBLE);
  }

  private static final String ARG_LOST_LIST = "LOSTS";
  private static final String ARG_FOUND_LIST = "FOUNDS";

  private boolean animation_done = true;
  private void init_pager(View view){
    pager = (ViewPager) view.findViewById(R.id.viewpager_feed);

    final FeedPanePagerAdapter pagerAdapter = new FeedPanePagerAdapter(
        getChildFragmentManager(), getActivity()
    );

    pager.setAdapter(pagerAdapter);
    pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        System.out.println("offset "+ positionOffset);
        System.out.println("offsetpixel "+ positionOffsetPixels);

      }

      @Override
      public void onPageSelected(int position) {

        switch (position) {
          case 0:
            animation_done = false;
            pager_label.setText(getString(R.string.foundfeedlistlabel));
            break;
          case 1:
            animation_done = false;
            pager_label.setText(getString(R.string.lostfeedlistlabel));
            break;
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  private void init_pager_label(View view){
    pager_label = (TextSwitcher) view.findViewById(R.id.pager_label);
    pager_label.setFactory(new ViewSwitcher.ViewFactory() {
      @Override
      public View makeView() {
        TextView t = new TextView(fa);
        t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        t.setTextSize(40);
        TypefaceFactory.format(t, "Futura Book font.ttf", fa);
        return t;
      }
    });
    pager_label.setText(getString(R.string.foundfeedlistlabel));
    /*
    TextView tv1 = (TextView) pager_label.findViewById(R.id.switchtv_1);
    TextView tv2 = (TextView) pager_label.findViewById(R.id.switchtv_2);
    TypefaceFactory.format(tv1, "futur.ttf", getActivity());
    TypefaceFactory.format(tv2, "futur.ttf", getActivity());
    */
    // Declare the in and out animations and initialize them
    animate_label();
  }

  private void animate_label(){

    Animation in = AnimationUtils.loadAnimation(fa, android.R.anim.fade_in);
    Animation out = AnimationUtils.loadAnimation(fa,android.R.anim.fade_out);

    // set the animation type of textSwitcher
    pager_label.setInAnimation(in);
    pager_label.setOutAnimation(out);
  }
}
