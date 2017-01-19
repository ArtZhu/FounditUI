package com.cuilinchen.mappart.activitychat;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.user.User;

/**
 * Created by Art on 5/11/16.
 */
public class UserProfileFragment extends Fragment {

  private User user;
  private ImageView rating, pic;
  private TextView user_name;

  public UserProfileFragment() {}

  public static UserProfileFragment newInstance(User user){
    UserProfileFragment f = new UserProfileFragment();
    Bundle args = new Bundle();
    args.putSerializable("User", user);
    f.setArguments(args);
    return f;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.userprofile, container, false);

    user_name = (TextView) v.findViewById(R.id.user_name);
    //rating = (ImageView) v.findViewById(R.id.user_rating);
    //pic = (ImageView) v.findViewById(R.id.user_pic);

    user_name.setText(user.email.substring(0, user.email.indexOf("@")));
    //rating.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.stars));
    //pic.setImageBitmap(BitmapFactory.decodeResource(getResources(), user.img));



    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    view.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Activity a = getActivity();
        if(a instanceof  OnUserProfileBack){
          ((OnUserProfileBack) a).onUserProfileBackButtonClicked();
        }
      }
    });
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    user = (User) getArguments().get("User");
  }

  public interface OnUserProfileBack{
    void onUserProfileBackButtonClicked();
  }
}
