package com.cuilinchen.mappart.viewFactory;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuilinchen.mappart.R;

/**
 * Created by CUILINCHEN on 5/6/16.
 */
public class FeedlistFactory {

  public static View slot(View v, Activity activity){
    ImageView img = (ImageView) v.findViewById(R.id.imageview_feed_list_slot);
    TextView tv_TITLE = (TextView) v.findViewById(R.id.textview_feed_list_slot_TITLE);
    TextView tv_DETAIL = (TextView) v.findViewById(R.id.textview_feed_list_slot_DETAIL);
    TextView tv_TIME = (TextView)  v.findViewById(R.id.textview_feed_list_slot_TIME);

    tv_TITLE.setTypeface(TypefaceFactory.font(
        "Futura Book font.ttf", activity
    ));
    tv_TITLE.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

    tv_DETAIL.setTypeface(TypefaceFactory.font(
        "Futura-Std-Light_19054.ttf", activity
    ));
    tv_DETAIL.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

    tv_TIME.setTypeface(TypefaceFactory.font(
        "Futura-Std-Light_19054.ttf", activity
    ));
    tv_TIME.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);


    return v;
  }
}
