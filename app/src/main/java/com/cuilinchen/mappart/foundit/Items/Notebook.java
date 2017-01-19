package com.cuilinchen.mappart.foundit.Items;

import com.cuilinchen.mappart.foundit.Questionnaire.ChoiceQuestion;
import com.cuilinchen.mappart.foundit.Questionnaire.StringQuestion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by CUILINCHEN on 5/11/16.
 */
public class Notebook extends Item {
  private Notebook _this = this;
  public static final Notebook dummy_notebook = (Notebook) new Notebook().pages("120").content("empty").color("Black");
  private String content;
  private int pages;
  @Override
  public String type() {
    return "Notebook";
  }

  public Notebook pages(String _pages){
    int p;
    try {
      p = Integer.parseInt(_pages);
    }catch (NumberFormatException e ){
      return _this;
    }
    pages = p;
    return _this;
  }

  public Notebook content(String _content){
    content = _content;
    return _this;
  }

  private String[] colorstrings = {
      "red", "yellow", "blue", "orange", "purple", "green", "black", "pink"
  };

  @Override
  public String generateRandomDetail() {
    Random r = new Random();
    return "It's a " + colorstrings[r.nextInt(colorstrings.length)] + " notebook with " + r.nextInt(120) + " pages.";
  }


  @Override
  public void init_questionnaire() {
    try {
      questionnaire.put(new StringQuestion("What's the color?", "color")
          , getClass().getMethod("color", String.class));
      questionnaire.put(new StringQuestion("Name some contents?", "category")
          , getClass().getMethod("category", String.class));
      questionnaire.put(new StringQuestion("How many pages are there?", "page number")
          , getClass().getMethod("pages", String.class));
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Notebook copyOf(boolean copy_attributes) {
    return (Notebook) new Notebook().pages("120").content("empty").color("Black");
  }

  @Override
  public Item fromJSON(JSONObject jsonObject) throws JSONException {
    return null;
  }
}
