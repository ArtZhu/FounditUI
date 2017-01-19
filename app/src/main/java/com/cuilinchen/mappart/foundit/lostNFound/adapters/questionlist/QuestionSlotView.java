package com.cuilinchen.mappart.foundit.lostNFound.adapters.questionlist;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cuilinchen.mappart.foundit.Questionnaire.Question;

/**
 * Created by Art on 5/8/16.
 */
public abstract class QuestionSlotView extends LinearLayout {
  protected final String tag;
  public final String getTag() {
    return tag;
  }

  protected final Question question;
  public final Question getQuestion() { return question; }

  protected Activity activity;
  protected ViewGroup root;

  public QuestionSlotView(Activity _activity, Question _question, ViewGroup _root){
    super(_activity);

    activity = _activity;
    root = _root;
    question = _question;
    tag = question.getTag();
  }

  public abstract Object getAnswer();
}
