package com.cuilinchen.mappart.feedactivity.model;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.support.v4.content.res.ResourcesCompat;

import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.foundit.Items.Dog;
import com.cuilinchen.mappart.foundit.Items.IPad;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.foundit.Items.Notebook;
import com.cuilinchen.mappart.foundit.Items.Phone;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Art on 5/9/16.
 */
public class SimpleFeed extends Feed {

  public SimpleFeed(String _title, String _detail, int _id, Timestamp _timestamp, Item _item, double _lat, double _lng, int _category) {
    super(_title, _detail, _id, _timestamp, _item, _lat, _lng, _category);
  }

  public static Feed fromJSON(JSONObject jsonFeed, Item _item) throws JSONException {
    String timestamp_string = jsonFeed.getString(JSON.TIMESTAMP_STRING);
    Feed ret = new SimpleFeed(

        jsonFeed.getString(JSON.TITLE_STRING),
        jsonFeed.getString(JSON.DETAIL_STRING),
        jsonFeed.getInt(JSON.ID_STRING),
        Timestamp.valueOf(timestamp_string),
        _item,
        jsonFeed.getDouble(JSON.LAT_STRING),
        jsonFeed.getDouble(JSON.LNG_STRING),
        jsonFeed.getInt(JSON.CATEGORY_STRING));

    return ret;
  }

  public static class Builder{
    public Builder detail(String bdetail) {
      this.bdetail = bdetail;
      return this;
    }

    public Builder id(int bid) {
      this.bid = bid;
      return this;
    }

    public Builder item(Item bitem) {
      this.bitem = bitem;
      return this;
    }

    public Builder lat(double blat) {
      this.blat = blat;
      return this;
    }

    public Builder lng(double blng) {
      this.blng = blng;
      return this;
    }

    public Builder timestamp(Timestamp btimestamp) {
      this.btimestamp = btimestamp;
      return this;
    }

    public Builder title(String btitle) {
      this.btitle = btitle;
      return this;
    }

    public Builder category(int bcategory) {
      this.bcategory = bcategory;
      return this;
    }

    private Item bitem;
    private int bid;
    private Timestamp btimestamp;
    private String btitle;
    private String bdetail;
    private double blat;
    private double blng;
    private int bcategory;

    public SimpleFeed build(){
      return new SimpleFeed(btitle, bdetail, bid, btimestamp, bitem, blat, blng, bcategory);
    }
  }


  //Aim at genearting feeds from another phone; mimic server
  public static class Generator{
    private static int id = 100;
    private static Item[] possible_types = {Phone.dummy_phone, Dog.dummy_dog, IPad.dummy_ipad, Notebook.dummy_notebook};
    private static String[] title_phrase_found = {"Found %s"};
    private static String[] title_phrase_lost = {"Lost %s", "Looking for a %s", "Forgot my %s", "%s missing", "If you saw an %s"};

    private static Random r = new Random();

    public static SimpleFeed generate(LatLng latLng, int category, Item sample, Context context){
      boolean isFound;

      switch (category) {
        case CATEGORY_FOUND:
          isFound = true;
          break;
        case CATEGORY_LOST:
          isFound = false;
          break;
        default:
          isFound = r.nextInt() % 2 == 0;
          break;
      }
      String title, detail;
      Timestamp timestamp;
      Item item;
      double dlat, dlng;

      if(sample == null){
        item = possible_types[r.nextInt(possible_types.length)];
      }else{
        item = Generator.generateRandomItem(sample, context);
      }

      if(isFound) {
        title = String.format(title_phrase_found[r.nextInt(title_phrase_found.length)], item.type());
        detail = item.generateRandomDetail();

      }else{
        item = possible_types[r.nextInt(possible_types.length)];
        title = String.format(title_phrase_lost[r.nextInt(title_phrase_lost.length)], item.type());
        detail = item.generateRandomDetail();
      }

      Calendar c = Calendar.getInstance();
      Long fake_ts = c.getTime().getTime() - Long.valueOf(r.nextInt(60 * 60 * 47 * 1000));
      timestamp = new Timestamp(fake_ts);
      dlat = r.nextDouble() % 180 - 90;
      dlng = r.nextDouble() % 180 - 90;

      return new SimpleFeed.Builder()
          .title(title)
          .detail(detail)
          .id(id++)
          .item(item)
          .lat(latLng.latitude + dlat)
          .lng(latLng.longitude + dlat)
          .timestamp(timestamp)
          .build();
    }

    public static Item generateRandomItem(Item sample, Context context){
      Item ret = sample.copyOf(true);

      if(phone_colors == null)
        phone_colors = context.getResources().getStringArray(R.array.phone_colors);
      if(dog_colors == null)
        dog_colors = context.getResources().getStringArray(R.array.dog_colors);

      switch (sample.type()){
        case "Phone":
          ret.color(phone_colors[r.nextInt(phone_colors.length)]);
          break;
        case "Dog":
          ret.color(dog_colors[r.nextInt(dog_colors.length)]);
          break;

      }

      return ret;
    }

    private static String[] phone_colors;
    private static String[] dog_colors;
  }

  /*
  private static long nextLong(Random rng, long UPPER_RANGE) {
    long randomValue =
        (long)(rng.nextDouble()*(UPPER_RANGE));
    return randomValue;
  }
  */
}
