package com.cuilinchen.mappart.settings;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Art on 5/5/16.
 */
public abstract class FlowSetting implements Setting {
  //must implement this interface to be in the first flow setting list;
  public abstract ArrayList<Choice> getChoices();

  public View getView(Activity activity){

    //drawing pane
    LinearLayout drawing_pane = new LinearLayout(activity);
    drawing_pane.setOrientation(LinearLayout.VERTICAL);

    //
    View prompt_view;
    {
      prompt_view = this.getPrompt().getView(activity);
      prompt_view.setLayoutParams(new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
          0,
          1.0f
      ));
    }
    drawing_pane.addView(prompt_view);

    //
    LinearLayout choices_pane;
    {
      choices_pane = new LinearLayout(activity);
      choices_pane.setOrientation(LinearLayout.HORIZONTAL);
      for (final Choice choice : getChoices()) {
        View v = choice.getView(activity);
        v.setLayoutParams(new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.0f
        ));
        v.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            choice.onClick();
          }
        });
        choices_pane.addView(v);
      }

      choices_pane.setLayoutParams(new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
          0,
          1.0f
      ));
    }
    drawing_pane.addView(choices_pane);

    return drawing_pane;
  }
}
