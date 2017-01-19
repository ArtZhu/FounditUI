package com.cuilinchen.mappart.locationrelatedfeeds;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Art on 5/11/16.
 */
public class RecyclerFeedlistFragment extends Fragment {

  //type, String -> resId
  private static HashMap<String, HashMap<String, Integer>> mIcons;
  private OnFeedSelected mListener;

  private Context context;

  public static RecyclerFeedlistFragment newInstance() {
    init_map();
    return new RecyclerFeedlistFragment();
  }

  public RecyclerFeedlistFragment() {}

  class RecyclerFeedAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<Feed> feedArrayList;

    public RecyclerFeedAdapter(Context context, ArrayList<Feed> _feedArrayList) {
      mLayoutInflater = LayoutInflater.from(context);
      feedArrayList = _feedArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      return new ViewHolder(mLayoutInflater
          .inflate(R.layout.recycler_feed_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
      final Feed feed = feedArrayList.get(position);
      final Item item = feed.getItem();

      System.out.println(item.getColor() + " " + item.type());
      final Integer i =  mIcons.get(item.type()).get(item.getColor());

      int imageResId = 0;
      if(i == null) {
        switch (item.type()){
          case "Phone":
            imageResId = R.mipmap.iphone_white;
            break;
          case "Dog":
            imageResId = R.mipmap.dog_brown;
            break;
          case "IPad":
            imageResId = R.mipmap.coming;
            break;
          case "Notebook":
            imageResId = R.mipmap.coming;
            break;
        }
      }
      else
        imageResId = i;

      System.out.println("!!!!??!K!L:@#!@");
      final String title = feed.getTitle();
      final String detail = feed.getDetail();
      final String time = feed.getTime();

      viewHolder.setData(imageResId, title, detail, time);

      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mListener.onFeedSelected(feed);
        }
      });
    }

    @Override
    public int getItemCount() {
      return feedArrayList.size();
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    // Views
    private ImageView mImageView;
    private TextView mTitleTextView;
    private TextView mDetailTextView;
    private TextView mTimeTextView;

    private ViewHolder(View itemView) {
      super(itemView);

      // Get references to image and name.
      mImageView = (ImageView) itemView.findViewById(R.id.recycler_img);
      mTitleTextView = (TextView) itemView.findViewById(R.id.recycler_title);
      mDetailTextView = (TextView) itemView.findViewById(R.id.recycler_detail);
      mTimeTextView = (TextView) itemView.findViewById(R.id.recycler_time);

      TypefaceFactory.format(mTitleTextView, "Futura Book font.ttf", context);
      TypefaceFactory.format(mDetailTextView, "Futura-Std-Light_19054.ttf", context);
      TypefaceFactory.format(mTimeTextView, "Futura-Std-Light_19054.ttf", context);
    }

    private void setData(int imageResId, String title, String detail, String time) {
      mImageView.setImageResource(imageResId);
      mTitleTextView.setText(title);
      mDetailTextView.setText(detail);
      mTimeTextView.setText(time);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;

    if (context instanceof OnFeedSelected) {
      mListener = (OnFeedSelected) context;
    } else {
      throw new ClassCastException(context.toString() + " must implement OnFeedSelected.");
    }
  }

  private Activity activity;
  private RecyclerView recyclerView;
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    activity = getActivity();

    final View view = inflater.inflate(R.layout.fragment_recycler_feed_list, container, false);

    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(linearLayoutManager);

    update();

    return view;
  }

  public void update(){

    if(activity instanceof OnFeedSelected){
      ArrayList<Feed> newFeeds = ((OnFeedSelected) activity).getFeeds();
      System.out.println("NEW:" + newFeeds);
      recyclerView.setAdapter(new RecyclerFeedAdapter(activity, newFeeds));
    }
  }

  public interface OnFeedSelected {
    void onFeedSelected(Feed feed);
    ArrayList<Feed> getFeeds();
  }


  private static void init_map(){
    mIcons = new HashMap<>();

    HashMap<String, Integer> phones = new HashMap<>();
    phones.put("Gold", R.mipmap.iphone_gold);
    phones.put("Rose", R.mipmap.iphone_rose);
    phones.put("White", R.mipmap.iphone_white);
    phones.put("Gray", R.mipmap.iphone_gray);
    phones.put("Black", R.mipmap.iphone_gray);


    HashMap<String, Integer> dogs = new HashMap<>();
    dogs.put("Black", R.mipmap.dog_black);
    dogs.put("White", R.mipmap.dog_white);
    dogs.put("Brown", R.mipmap.dog_brown);

    HashMap<String, Integer> ipads = new HashMap<>();
    ipads.put("Black", R.mipmap.coming);

    HashMap<String, Integer> notebooks = new HashMap<>();
    notebooks.put("Black", R.mipmap.coming);

    mIcons.put("Phone", phones);
    mIcons.put("Dog", dogs);
    mIcons.put("IPad", ipads);
    mIcons.put("Notebook", notebooks);
  }
}

