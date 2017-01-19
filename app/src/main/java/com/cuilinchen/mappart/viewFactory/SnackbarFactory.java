package com.cuilinchen.mappart.viewFactory;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.cuilinchen.mappart.R;

/**
 * Created by CUILINCHEN on 5/8/16.
 */
public class SnackbarFactory {

  public static Snackbar submissionSnackbar(Activity activity, View v, String text, int show_length, View.OnClickListener reset_listener){
    Snackbar snackbar = Snackbar.make(v, text, show_length);
    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
// Hide the text
    TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
    textView.setVisibility(View.INVISIBLE);

// Inflate our custom view
    View snackView = activity.getLayoutInflater().inflate(R.layout.submission_snackbar, null);
// Configure the view
    TextView tv_message = (TextView) snackView.findViewById(R.id.tv_message);
    TextView tv_button = (TextView) snackView.findViewById(R.id.tv_button);
    tv_message.setText(text);
    tv_message.setTextColor(Color.WHITE);

    tv_button.setText("Undo");
    tv_button.setOnClickListener(reset_listener);

// Add the view to the Snackbar's layout
    layout.addView(snackView, 0, new Snackbar.SnackbarLayout.LayoutParams(
        Snackbar.SnackbarLayout.LayoutParams.MATCH_PARENT,
        Snackbar.SnackbarLayout.LayoutParams.MATCH_PARENT
    ));

    return snackbar;
  }

}
