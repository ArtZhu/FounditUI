package com.cuilinchen.mappart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cuilinchen.mappart.Database.UserDBManager;
import com.cuilinchen.mappart.foundit.Items.Phone;
import com.cuilinchen.mappart.settings.activity.FlowSettingActivity;

/**
 * Created by CUILINCHEN on 5/11/16.
 */
public class LoadingScreenActivity extends AppCompatActivity {
  private final LoadingScreenActivity _this = this;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading_screen);

    getWindow().setStatusBarColor(R.color.gray_default);


    /*
    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.root_frame_layout, SettingFragment.newInstance(flowSettings.get(0)), "rageComicList")
          .commit();
    }
    */
  }

  public void goNext(){
    Intent i = new Intent(_this, FlowSettingActivity.class);
    startActivity(i);
    finish();
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    Phone.InitPhoneBrands task1 = new Phone.InitPhoneBrands();
    if(!UserDBManager.exist(_this, UserDBManager.PhoneDBName)){
      task1.execute(_this);
      //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      //    .setAction("Action", null).show();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

}
