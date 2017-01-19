package com.cuilinchen.mappart.FeedDetailActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;

/**
 * Created by CUILINCHEN on 5/11/16.
 */
public class ContactBarFragment extends Fragment {

  private OnContactButtonDown mListener;

  private Context context;
  private TextView contact_button;

  public static ContactBarFragment newInstance() {
    return new ContactBarFragment();
  }

  public ContactBarFragment() {}

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;

    if (context instanceof OnContactButtonDown) {
      mListener = (OnContactButtonDown) context;
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

    final View view = inflater.inflate(R.layout.contact_bottombar, container, false);

    contact_button = (TextView) view.findViewById(R.id.contact_button);
    activity = getActivity();
    if(activity instanceof OnContactButtonDown)
      contact_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ((OnContactButtonDown) activity).onContactButtonClick();
        }
      });

    TypefaceFactory.format(contact_button, "Futura Book font.ttf", context);

    return view;
  }
  public interface OnContactButtonDown {
    void onContactButtonClick();
  }
}

