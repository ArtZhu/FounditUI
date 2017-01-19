package com.cuilinchen.mappart.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BackgroundFeedRetrieverService extends Service {
  public BackgroundFeedRetrieverService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }


}
