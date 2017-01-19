package com.cuilinchen.mappart.FeedDetailActivity;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuilinchen.mappart.Database.SettingDBManager;
import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.activitychat.ChatFragment;
import com.cuilinchen.mappart.activitychat.UserProfileFragment;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.feedactivity.model.SimpleFeed;
import com.cuilinchen.mappart.foundit.Items.Dog;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.foundit.Items.Phone;
import com.cuilinchen.mappart.foundit.lostNFound.FindActivity;
import com.cuilinchen.mappart.user.User;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Art on 5/11/16.
 */

public class DetailActivity extends AppCompatActivity implements ContactBarFragment.OnContactButtonDown, UserProfileFragment.OnUserProfileBack {
  private DetailActivity _this = this;

  private ImageView img_icon;
  private TextView title_field, info_label, info_field, detail_label, detail_field;

  private Item currentSectionItem;
  private Feed currentSectionFeed;

  private ContactBarFragment contactBarFragment;
  private ImageView button_back;

  private static HashMap<String, HashMap<String, Integer>> mIcons = new HashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //configureTheme();
    setContentView(R.layout.activity_detail);

    init_map();

    img_icon = (ImageView) findViewById(R.id.detail_icon);
    title_field = (TextView) findViewById(R.id.detail_tv_title);

    info_label = (TextView) findViewById(R.id.detail_tv_info_label);
    info_field = (TextView) findViewById(R.id.detail_tv_info);
    detail_label = (TextView) findViewById(R.id.detail_tv_other_details_label);
    detail_field = (TextView) findViewById(R.id.detail_tv_other_details);

    TypefaceFactory.format(title_field, "Futura-Std-Light_19054.ttf", _this);
    TypefaceFactory.format(info_label, "Futura-Std-Light_19054.ttf", _this);
    TypefaceFactory.format(info_field, "Futura-Std-Light_19054.ttf", _this);
    TypefaceFactory.format(detail_label, "Futura-Std-Light_19054.ttf", _this);
    TypefaceFactory.format(detail_field, "Futura-Std-Light_19054.ttf", _this);

    Intent i = getIntent();
    Bundle arg = i.getExtras();

    button_back = (ImageView) findViewById(R.id.button_back);
    button_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    if(arg != null) {
      currentSectionFeed = (Feed) arg.getSerializable(DetailActivity.FEED_ARG);
    }
    if(arg == null || currentSectionFeed == null){
      Location l = ((LocationManager) getSystemService(LOCATION_SERVICE)).getLastKnownLocation(LocationManager.GPS_PROVIDER);
      currentSectionFeed = SimpleFeed.Generator.generate(
          new LatLng(l.getLatitude(), l.getLongitude()), Feed.CATEGORY_FOUND, Phone.dummy_phone, getApplicationContext());
    }
    currentSectionItem = currentSectionFeed.getItem();

    title_field.setText(currentSectionFeed.getTitle());
    info_field.setText(String.format(
        "This is a %s %s",
        currentSectionItem.getColor(), currentSectionItem.type()));
    detail_field.setText(currentSectionItem.generateRandomDetail());

    System.out.println("iconnull" + mIcons == null);
    System.out.println("item " + currentSectionItem.type());
    System.out.println("feednull" + currentSectionFeed == null);

    int resId;
    try {
      resId = mIcons.get(currentSectionItem.type()).get(currentSectionItem.getColor());
    }catch (Exception e){
      resId = R.mipmap.iphone_white;
    }

    img_icon.setImageBitmap(BitmapFactory.decodeResource(getResources(), resId));

  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);


    contactBarFragment = ContactBarFragment.newInstance();
    getSupportFragmentManager()
        .beginTransaction()
        .setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_bottom)
        .replace(R.id.contact_placer, contactBarFragment, "ContactBar")
        .commit();

  }

  private static void init_map(){
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

    mIcons.put(Phone.dummy_phone.type(), phones);
    mIcons.put(Dog.dummy_dog.type(), dogs);
  }

  public static final String FEED_ARG = "FEED!!!";

  @Override
  public void onContactButtonClick() {
    //
    //Toast.makeText(_this, "Contact!", Toast.LENGTH_SHORT).show();

    enableContact();
  }

  @Override
  public void onUserProfileBackButtonClicked() {
    disableContact();
  }

  private ChatFragment chatFragment = null;
  private UserProfileFragment userProfileFragment = null;

  public void enableContact(){
    fadeOut(button_back);
    if(chatFragment == null){
      chatFragment = ChatFragment.newInstance(User.Martha, User.Bear);
      userProfileFragment = UserProfileFragment.newInstance(User.Bear);


      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_bottom)
          .add(R.id.detail_root_frame, chatFragment)
          .commit();
      ft = getSupportFragmentManager().beginTransaction();
      ft.setCustomAnimations(R.animator.enter_from_top, R.animator.exit_to_top)
          .add(R.id.detail_root_frame, userProfileFragment)
          .commit();
    }else{

      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_bottom)
          .attach(chatFragment)
          .commit();
      ft = getSupportFragmentManager().beginTransaction();
      ft.setCustomAnimations(R.animator.enter_from_top, R.animator.exit_to_top)
          .attach(userProfileFragment)
          .commit();


    }
  }

  public void disableContact(){
    fadeIn(button_back);
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.setCustomAnimations(R.animator.enter_from_bottom, R.animator.exit_to_bottom)
        .detach(chatFragment)
        .commit();
    ft = getSupportFragmentManager().beginTransaction();
    ft.setCustomAnimations(R.animator.enter_from_top, R.animator.exit_to_top)
        .detach(userProfileFragment)
        .commit();
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

}
