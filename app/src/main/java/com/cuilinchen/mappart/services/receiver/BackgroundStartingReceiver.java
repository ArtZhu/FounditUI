package com.cuilinchen.mappart.services.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cuilinchen.mappart.services.BackgroundFeedRetrieverService;

/**
 * Created by Art on 5/7/16.
 */
public class BackgroundStartingReceiver extends BroadcastReceiver{

  private static final String TAG = "BackgroundStarter";

  @Override
  public void onReceive(Context context, Intent intent) {
    context.startService(new Intent(context.getApplicationContext(), BackgroundFeedRetrieverService.class));
  }
}
