package com.cuilinchen.mappart.foundit.lostNFound;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cuilinchen.mappart.Database.SettingDBManager;
import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.FeedActivity;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.foundit.Items.Dog;
import com.cuilinchen.mappart.foundit.Items.IPad;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.foundit.Items.Notebook;
import com.cuilinchen.mappart.foundit.Items.Phone;
import com.cuilinchen.mappart.foundit.Questionnaire.Question;
import com.cuilinchen.mappart.foundit.Questionnaire.StringQuestion;
import com.cuilinchen.mappart.foundit.lostNFound.adapters.ItemTypeAdapter;
import com.cuilinchen.mappart.foundit.lostNFound.adapters.questionlist.QuestionListAdapter;
import com.cuilinchen.mappart.foundit.lostNFound.adapters.questionlist.QuestionSlotView;
import com.cuilinchen.mappart.viewFactory.SnackbarFactory;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CUILINCHEN on 5/9/16.
 */
public abstract class FindFoundActivitySkeleton extends AppCompatActivity {
  protected FindFoundActivitySkeleton _this = this;
  protected int category;

  protected ListView lv_questions;
  protected AutoCompleteTextView textinput_item_type;
  protected TextView button_submit;
  protected ImageView imgbutton_back;
  protected TextView category_label;

  protected List<Item> DEFAULT_ITEM_TYPES = new ArrayList<>();

  public List<Item> getDEFAULT_ITEM_TYPES() {
    return DEFAULT_ITEM_TYPES;
  }
  private Item currentItem;

  /**
   * ATTENTION: This was auto-generated to implement the App Indexing API.
   * See https://g.co/AppIndexing/AndroidStudio for more information.
   */
  private GoogleApiClient client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    configureTheme();
    setContentView(R.layout.activity_find);

    //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);

    DEFAULT_ITEM_TYPES.add(Phone.dummy_phone);
    DEFAULT_ITEM_TYPES.add(Dog.dummy_dog);
    DEFAULT_ITEM_TYPES.add(IPad.dummy_ipad);
    DEFAULT_ITEM_TYPES.add(Notebook.dummy_notebook);

    category_label = (TextView) findViewById(R.id.category_label);
    TypefaceFactory.format(category_label, "Futura Book font.ttf", _this);
    switch (category){
      case Feed.CATEGORY_FOUND:
        category_label.setText(getString(R.string.found_string));
        break;
      case Feed.CATEGORY_LOST:
        category_label.setText(getString(R.string.lost_string));
        break;
    }

    lv_questions = (ListView) findViewById(R.id.listview_questions);
    //spinner default item question;
    lv_questions.setAdapter(new QuestionListAdapter(_this, 0,
        new ArrayList<Question>()));

    imgbutton_back = (ImageView) findViewById(R.id.imgbutton_back);
    imgbutton_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    button_submit = (TextView) findViewById(R.id.button_submit);
    button_submit.setVisibility(View.INVISIBLE);
    button_submit.setTypeface(TypefaceFactory.font("Futura-Std-Light_19054.ttf", _this));
    button_submit.setOnClickListener(new View.OnClickListener() {
      private Snackbar m;
      private boolean send = true;

      @Override
      public void onClick(View v) {
        m = SnackbarFactory.submissionSnackbar(_this, lv_questions, "Submitted", Snackbar.LENGTH_SHORT,
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                setNotSend();
              }
            }
        ).setCallback(new Snackbar.Callback() {
          @Override
          public void onDismissed(Snackbar snackbar, int event) {
            super.onDismissed(snackbar, event);
            if (send) {
              //SEND!
              Intent i = new Intent(_this, FeedActivity.class);
              System.out.println("Starting on " + currentItem.type());
              int count = lv_questions.getAdapter().getCount();
              for(int j=0; j<count; j++){
                Question q = (Question) lv_questions.getAdapter().getItem(j);
                if(q instanceof StringQuestion){
                  EditText e = (EditText) lv_questions.getChildAt(j).findViewById(R.id.slot_edittext);
                  currentItem.setAnswer(q, e.getText().toString());
                }else{
                  Spinner s = (Spinner) lv_questions.getChildAt(j).findViewById(R.id.slot_spinner);
                  currentItem.setAnswer(q, s.getSelectedItem());
                }
              }
              
              i.putExtra(ITEM_ARG, currentItem);
              i.putExtra(CATEGORY_ARG, category);
              System.out.println(currentItem.type() + " " + currentItem.getColor());
              startActivity(i);
            }
            else{
              send = true;
            }
          }
        });
        m.show();
      }

      private void setNotSend() {
        send = false;

        m.dismiss();
      }
    });

    textinput_item_type = (AutoCompleteTextView) findViewById(R.id.textinput_item_type);
    textinput_item_type.setAdapter(new ItemTypeAdapter(_this, R.layout.itemtypeslot_string, DEFAULT_ITEM_TYPES));
    textinput_item_type = (AutoCompleteTextView) TypefaceFactory.format(textinput_item_type, "Futura-Std-Light_19054.ttf", _this);
    textinput_item_type.setDropDownBackgroundResource(R.color.transparent_50);
    textinput_item_type.addTextChangedListener(new TextWatcher() {
      private Item chosen;

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      boolean updated = false;

      @Override
      public void afterTextChanged(Editable s) {
        if (!updated) {
          updated = true;
          String type = s.toString();
          for (Item i : DEFAULT_ITEM_TYPES)
            if (i.isOfType(type)) {
              chosen = i;
              updateView();
              setItemChosen(chosen);
              return;
            }

          chosen = null;
          updateView();
          return;
        }
      }

      private void updateView() {
        if (chosen != null) {
          button_submit.setVisibility(View.VISIBLE);
          //textinput_item_type.setText(chosen.type());

          ArrayAdapter adapter = new QuestionListAdapter(_this, 0, chosen.questionnaire());
          lv_questions.setAdapter(adapter);
          adapter.notifyDataSetChanged();

          //textinput_item_type.clearFocus();
          //findViewById(R.id.layout_find_main).requestFocus();
        }
        updated = false;
      }
    });

    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

  }

  private void setItemChosen(Item _item){
    currentItem = _item;
  }



  private Item getItem() throws NoSuchMethodException {
    String type = textinput_item_type.getText().toString();
    for (Item item : DEFAULT_ITEM_TYPES) {
      if (item.isOfType(type)) {
        Item ret = item.copyOf(true);
        for (int i = 0; i < lv_questions.getChildCount(); i++) {
          QuestionSlotView v = ((QuestionSlotView) lv_questions.getChildAt(i));
          item.setAnswer((Question) lv_questions.getAdapter().getItem(i), v.getAnswer());
        }
        return ret;
      }
    }
    return null;
  }

  public static final String ITEM_ARG = "ITEM!!";
  public static final String CATEGORY_ARG = "CATEGORY";


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
}