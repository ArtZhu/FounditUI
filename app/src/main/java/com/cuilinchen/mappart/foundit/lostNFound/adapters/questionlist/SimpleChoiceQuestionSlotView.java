package com.cuilinchen.mappart.foundit.lostNFound.adapters.questionlist;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.foundit.Questionnaire.Question;

/**
 * Created by Art on 5/8/16.
 */
public class SimpleChoiceQuestionSlotView extends QuestionSlotView {
  private Spinner spinner;

  public SimpleChoiceQuestionSlotView(Activity _activity, ViewGroup _root, Question _question) {
    super(_activity, _question, _root);


    //inflate(activity, R.layout.questionslot_spinner, root);
    spinner = (Spinner) findViewById(R.id.slot_spinner);

    super.setLayoutParams(this.getLayoutParams());
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();


  }

  @Override
  public Object getAnswer() {
    return spinner.getSelectedItem();
  }
}
