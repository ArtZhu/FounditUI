package com.cuilinchen.mappart.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cuilinchen.mappart.NetworkManager;
import com.cuilinchen.mappart.feedactivity.model.Feed;

import java.net.URL;
import java.util.ArrayList;

public class FeedRetrieverService extends Service {
  private ArrayList<AsyncTask<URL, Integer, ArrayList<Feed>>> asyncTasks = new ArrayList<>();

  private NetworkManager networkManager;

  public FeedRetrieverService() {}

  public static FeedRetrieverService newInstance(
      NetworkManager _networkManager) {
    FeedRetrieverService frs = new FeedRetrieverService();
    frs.setNetworkManager(_networkManager);
    return frs;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    //getApplicationContext().openOrCreateDatabase(FeedActivity.FEED_DATABASE_NAME, );
        //retrieve Binder

    return START_STICKY;

  }

  public NetworkManager getNetworkManager() {
    return networkManager;
  }

  public void setNetworkManager(NetworkManager networkManager) {
    this.networkManager = networkManager;
  }

  public class RetrieverBinder extends Binder{

  }

}
