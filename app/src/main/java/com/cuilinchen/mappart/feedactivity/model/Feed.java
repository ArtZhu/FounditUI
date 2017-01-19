package com.cuilinchen.mappart.feedactivity.model;

import com.cuilinchen.mappart.foundit.Items.Item;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Art on 5/6/16.
 */
public abstract class Feed implements Comparable<Feed>, Serializable {
  protected final Item item;
  protected final int id;
  protected final Timestamp timestamp;
  protected String title;
  protected String detail;
  public final double lat;
  public final double lng;
  public final int category;

  public static final int CATEGORY_LOST = 1392100;
  public static final int CATEGORY_FOUND = 1382901;

  public Feed(String _title, String _detail, int _id, Timestamp _timestamp, Item _item, double _lat, double _lng, int _category){
    id = _id;
    timestamp = _timestamp;
    item = _item;
    lat = _lat;
    lng = _lng;
    title = _title;
    detail = _detail;
    category = _category;
  }

  public String getTitle(){return title;}
  public String getDetail(){return detail;}
  public Item getItem() {return item;}
  public int getId(){return id;}

  public String getTime() {
    Date current = Calendar.getInstance().getTime();
    long diff = current.getTime() - timestamp.getTime();
    String ret;
    if(diff > day_diff) {
      String s = "";
      long day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
      if(day > 1)
        s = "s";
      ret = String.format("%d day%s ago", day, s);
    }
    else {
      ret = "";
      long hour = TimeUnit.MILLISECONDS.toHours(diff);
      long min = TimeUnit.MILLISECONDS.toMinutes(diff - hour * 60 * 60 * 1000);

      if (TimeUnit.MILLISECONDS.toHours(diff) != 0)
        ret += String.format("%d h ", hour);
      ret += String.format("%d min ago", min);
    }

    return ret;
  }


  @Override
  public int compareTo(Feed another) {
    if(timestamp.after(another.timestamp))
      return -1;
    else
      return 1;
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Feed))
      return false;

    Feed f = (Feed) o;
    return f.id == this.id;
  }

  public class JSON{
    public static final String ID_STRING = "ID";
    public static final String TIMESTAMP_STRING = "TIMESTAMP";
    public static final String TITLE_STRING = "TITLE";
    public static final String DETAIL_STRING = "DETAIL";
    public static final String LAT_STRING = "LAT";
    public static final String LNG_STRING = "LNG";
    public static final String CATEGORY_STRING = "CATEGORY";
  }

  protected final long day_diff = 86400000L;
  public static final String ADDRESS_STRING = "ADDRJKLFSD:";
}
