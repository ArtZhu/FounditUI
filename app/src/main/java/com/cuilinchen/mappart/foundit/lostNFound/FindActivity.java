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
import android.widget.ListView;
import android.widget.TextView;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.feedactivity.FeedActivity;
import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.foundit.FindFoundScreen;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.foundit.Items.Phone;
import com.cuilinchen.mappart.foundit.Questionnaire.Question;
import com.cuilinchen.mappart.foundit.lostNFound.adapters.ItemTypeAdapter;
import com.cuilinchen.mappart.foundit.lostNFound.adapters.questionlist.QuestionListAdapter;
import com.cuilinchen.mappart.foundit.lostNFound.adapters.questionlist.QuestionSlotView;
import com.cuilinchen.mappart.viewFactory.SnackbarFactory;
import com.cuilinchen.mappart.viewFactory.TypefaceFactory;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CUILINCHEN on 5/10/2016.
 */

public class FindActivity extends FindFoundActivitySkeleton{

  public FindActivity() {
    super();
    category = Feed.CATEGORY_LOST;
  }
}