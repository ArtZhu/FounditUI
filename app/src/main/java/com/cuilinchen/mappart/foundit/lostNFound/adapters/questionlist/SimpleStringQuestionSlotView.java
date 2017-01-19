package com.cuilinchen.mappart.foundit.lostNFound.adapters.questionlist;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.foundit.Questionnaire.Question;
import com.cuilinchen.mappart.foundit.Questionnaire.StringQuestion;

/**
 * Created by Art on 5/8/16.
 */
public class SimpleStringQuestionSlotView extends QuestionSlotView {
  private EditText edittext;

  public SimpleStringQuestionSlotView(Activity _activity, ViewGroup _root, Question _question) {
    super(_activity, _question, _root);


    inflate(activity, R.layout.questionslot_edittext, root);
    edittext = (EditText) findViewById(R.id.slot_edittext);

    super.setLayoutParams(this.getLayoutParams());
  }

  @Override
  protected void onFinishInflate() {
    System.out.println("finish");
    super.onFinishInflate();

    TextView tv = (TextView) findViewById(R.id.slot_tv_edittext);
    final EditText txt = (EditText) findViewById(R.id.slot_edittext);

    tv.setText(question.getQuestion());
    txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
          ((StringQuestion) question).setAnswer(txt.getText().toString());
        }
      }
    });
  }

  @Override
  public String getAnswer() {
    return edittext.getText().toString();
  }
}
