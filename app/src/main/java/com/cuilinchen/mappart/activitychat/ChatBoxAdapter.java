package com.cuilinchen.mappart.activitychat;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.user.User;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by CUILINCHEN on 5/11/16.
 */
public class ChatBoxAdapter extends ArrayAdapter<ChatMessage> {
  private List<ChatMessage> msgs;
  private Activity activity;
  private ChatFragment chatFragment;


  public ChatBoxAdapter(Activity _activity, ChatFragment _chatFragment, int resource, List<ChatMessage> _msgs) {
    super(_activity, resource, _msgs);

    msgs = _msgs;
    activity = _activity;
    chatFragment = _chatFragment;
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    /*
    LinearLayout ll = new LinearLayout(getContext());
    ll.setOrientation(LinearLayout.HORIZONTAL);
    ll.setWeightSum(6.0f);

*/
    View ret;
    ChatMessage msg = msgs.get(position);
    if(chatFragment.getLocal_user().equals(msg.user)){
      /*
      ImageView imageView = new ImageView(activity);
      TextView tv = new TextView(activity);

      LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
          0,
          ViewGroup.LayoutParams.MATCH_PARENT,
          1.0f
      );
      p.setMarginEnd(10);
      imageView.setLayoutParams(p);
      tv.setLayoutParams(new LinearLayout.LayoutParams(
          0,
          ViewGroup.LayoutParams.MATCH_PARENT,
          3.0f
      ));
      TypefaceFactory.format(tv, "Futura-Std-Light_19054.ttf", activity);

      ll.addView(imageView, 0);
      ll.addView(tv, 1);
      */
      ret = View.inflate(activity, R.layout.local, null);

      System.out.println("!!!1!!!");
    }else{
      /*
      ImageView imageView = new ImageView(activity);
      TextView tv = new TextView(activity);
      View v = new View(activity);

      v.setLayoutParams(new LinearLayout.LayoutParams(
          0,
          ViewGroup.LayoutParams.MATCH_PARENT,
          //ViewGroup.LayoutParams.WRAP_CONTENT,
          2.0f
      ));

      LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
          0,
          ViewGroup.LayoutParams.MATCH_PARENT,
          //ViewGroup.LayoutParams.WRAP_CONTENT,
          1.0f
      );
      p.setMarginStart(10);
      imageView.setLayoutParams(p);
      tv.setLayoutParams(new LinearLayout.LayoutParams(
          0,
          ViewGroup.LayoutParams.MATCH_PARENT,
          //ViewGroup.LayoutParams.WRAP_CONTENT,
          3.0f
      ));
      TypefaceFactory.format(tv, "Futura-Std-Light_19054.ttf", activity);


      ll.addView(v, 0);
      ll.addView(tv, 1);
      ll.addView(imageView, 2);

      tv.setText(msg.content);
      */


      ret = View.inflate(activity, R.layout.remote, null);;

      System.out.println("!!!2!!!");
    }

    TextView tv = ((TextView) ret.findViewById(R.id.txt));
    TypefaceFactory.format(tv, "Futura-Std-Light_19054.ttf", activity);
    tv.setText(msg.content);

    ImageView imageView = (ImageView) ret.findViewById(R.id.img);
    imageView.setImageResource(msg.user.img);
    //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

    return ret;

    /*
    ll.setBackground(activity.getDrawable(R.drawable.roundedcorner_rect_50_10dp));

    ll.setLayoutParams(new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    ));
    return ll;
    */
  }

  @Override
  public int getCount() {
    return msgs.size() < 10? msgs.size() : 10;
  }
}
