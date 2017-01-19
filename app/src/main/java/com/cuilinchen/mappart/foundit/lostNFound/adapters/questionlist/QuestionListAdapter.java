package com.cuilinchen.mappart.foundit.lostNFound.adapters.questionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.foundit.Questionnaire.ChoiceQuestion;
import com.cuilinchen.mappart.foundit.Questionnaire.Question;
import com.cuilinchen.mappart.foundit.Questionnaire.StringQuestion;
import com.cuilinchen.mappart.foundit.lostNFound.FindActivity;
import com.cuilinchen.mappart.foundit.lostNFound.FindFoundActivitySkeleton;
import com.cuilinchen.mappart.foundit.lostNFound.adapters.ChoicesAdapter;
import com.cuilinchen.mappart.viewFactory.QuestionFactory;

import java.util.List;

/**
 * Created by Art on 5/7/16.
 */

public class QuestionListAdapter extends ArrayAdapter<Question> {
  private List<Question> questions;
  private FindFoundActivitySkeleton activity;
  private LayoutInflater inf;
  private int resource;

  public QuestionListAdapter(FindFoundActivitySkeleton _activity, int _resource, List<Question> _questions) {
    super(_activity, _resource,R.id.slot_tv_spinner, _questions);

    activity = _activity;
    resource = _resource;
    questions = _questions;
    inf = activity.getLayoutInflater();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final Question question = questions.get(position);

    View v = null;

    if (question instanceof ChoiceQuestion) {
      //final View v = new SimpleChoiceQuestionSlotView(activity, null, question);

      v = inf.inflate(R.layout.questionslot_spinner, null, false);

      //return new View(activity);


      TextView tv = (TextView) v.findViewById(R.id.slot_tv_spinner);
      final Spinner sp = (Spinner) v.findViewById(R.id.slot_spinner);

      tv.setText(question.getQuestion());

      sp.setAdapter(
          new ChoicesAdapter(
              activity, R.layout.support_simple_spinner_dropdown_item,
              ((ChoiceQuestion) question).getAllChoices()));

      sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          ((ChoiceQuestion) question).setChoices(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
          ((ChoiceQuestion) question).setChoices(0);
        }
      });
    }
    if (question instanceof StringQuestion) {
      //v = new SimpleStringQuestionSlotView(activity, null, question);

      v = inf.inflate(R.layout.questionslot_edittext, null);

      TextView tv = (TextView) v.findViewById(R.id.slot_tv_edittext);
      final EditText txt = (EditText) v.findViewById(R.id.slot_edittext);

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

    /*
    if (v != null) {
      v.setLayoutParams(new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT
      ));
    }
    */

    return QuestionFactory.question(v, activity);
  }

  @Override
  public void notifyDataSetChanged() {
    super.notifyDataSetChanged();

    //focus!
  }
}

