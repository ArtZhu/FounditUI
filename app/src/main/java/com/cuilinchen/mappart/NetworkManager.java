package com.cuilinchen.mappart;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.cuilinchen.mappart.feedactivity.model.Feed;
import com.cuilinchen.mappart.feedactivity.model.SimpleFeed;
import com.cuilinchen.mappart.foundit.Interface.Consumer;
import com.cuilinchen.mappart.foundit.Items.Item;
import com.cuilinchen.mappart.foundit.Items.Phone;
import com.cuilinchen.mappart.locationrelatedfeeds.FeedDetailsFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;


/**
 * Created by Art on 4/27/2016.
 */
public class NetworkManager {
  private static String base_url = "cscilab.bc.edu/~zhoude/MOBILE/mobilewebsite.php?request=";

  private static String latlng_zip_requets = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&sensor=true";

  private static String URL_phone = "http://www.phonearena.com/phones/manufacturers";
  private static String URL_dog = "http://dogtime.com/dog-breeds/profiles";

  private static String dog_regex = "";
  private static Pattern dog_pattern = null;

  private static String phone_regex = "";
  private static Pattern phone_pattern = null;

  private static Random r = new Random();


  public void request(String request, Consumer consumer) {
    String url = base_url + request;
    Request r = new Request();
    r.execute(url, consumer);

    Elements content;
  }

  /*
  public static List<Address> request(Geocoder geocoder, LatLng latlng){
    String url = String.format(latlng_zip_requets, latlng.latitude, latlng.longitude);

  }
  */

  public static ArrayList<Feed> requestFeedForLocation(Context context, int number, int category, Item sample, LatLng... latlngs){
    ArrayList<Feed> ret = new ArrayList<>();

    try {
      while (number-- > 0) {
        LatLng selected = latlngs[r.nextInt(latlngs.length)];
        ret.add(SimpleFeed.Generator.generate(selected, category, sample, context));
      }
    }catch (ExceptionInInitializerError e){
      System.out.println(e.getCause());
    }

    return ret;
  }

  public static ArrayList<String> retrieveInformationAbout(Item item){
    String web;
    ArrayList<String> ret = new ArrayList<>();


    int i = -1;
    switch(item.type()){
      case "Phone":
        web = getUrl(URL_phone);
        System.out.println(web);
        while((i = web.indexOf("manufacturers/")) > 0){

          web = web.substring(i + 1);
          //System.out.println(html.substring(0, 100));
          String brand = web.substring(web.indexOf("/") + 1, web.indexOf("\""));
          if(!ret.contains(brand))
            ret.add(brand);
          System.out.println("BRAND : " + brand);
        }
        break;
      case "Dog":
        web = scrapeWebpage(URL_dog);

        while((i = web.indexOf("dog-breeds/")) > 0){
          web = web.substring(i + 1);
          i = web.indexOf(">");
          web = web.substring(i + 1);
          i = web.indexOf("<");
          ret.add(web.substring(0, i));
          web = web.substring(i + 1);
        }

        break;
    }

    System.out.println("RET: " + ret);
    return ret;
  }

  public static String scrapeWebpage(String url){
    String ret = "";
    Document doc = null;
    try {
      doc = Jsoup.connect(url).get();
    } catch (IOException e) {    }

    for(Element e: doc.getAllElements())
      ret += e.text() + ":";

    return ret;
  }

  public static String getUrl(String url){
    BufferedReader in = null;
    String outputText = "";
    Document doc;
    try{

	    	/*
	    	doc = Jsoup.connect(url).get();

	    	System.out.println();

	    	Elements e = doc.select("a[href]");
	    	System.out.println(e);

	    	for(Element e1: e)
	    		String tag = e1.toString()
	    	System.out.println();System.out.println();System.out.println();System.out.println();
	    	*/
	    	/*
	    	for(Element e: doc.getAllElements()){
	    		if(e.toString().contains("dog-breeds"));
	    		System.out.println(e.toString());
	    		String E = e.toString();
	    		int i=-1;
	    		while((i = E.indexOf("dog-breeds")) > 0){
	    			E = E.substring(i);
	    			System.out.println(E.substring(11, E.indexOf("\"")));
	    		}

	    		//System.out.println(e.attributes());
	    	}
				*/
      URL u = new URL(url);
      in = new BufferedReader(new InputStreamReader(u.openConnection().getInputStream()));

      String line = "";
      while((line = in.readLine()) != null){
        outputText += line;
        //System.out.println(line);
      }
      in.close();
    }catch(IOException e){
      System.out.println("There was an error connecting to the URL");
      return "";
    }
    return outputText;
  }

  public static String getStringFromURL(String url){
    InputStream is = null;
    String result = "";
    HttpURLConnection con = null;

    try {
      con = (HttpURLConnection) (new URL(url).openConnection());
      con.setRequestMethod("GET");
      con.setDoInput(true);
      con.connect();

    } catch (Exception e) {
      Log.e("NetworkManager", "Error in http connection to " + url);
    }

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println("LINE: " + line);
        sb.append(line + "\n");
      }
      is.close();
      con.disconnect();
      result = sb.toString();
    } catch (Exception e) {
      Log.e("NetworkManager", "Error converting result" + e.toString());
    }

    return result;
  }


  class Request extends AsyncTask<Object, String, Void> {

    @Override
    protected Void doInBackground(Object... params) {
      String url = (String) params[0];
      Consumer u = (Consumer) params[1];
      u.consume(getJSONfromURL(url));
      return null;
    }

  }

  public static JSONObject getJSONfromURL(String url) {
    JSONObject jObj = null;

    try {
      jObj = new JSONObject(getStringFromURL(url));
    } catch (JSONException e) {
      Log.e("NetworkManager", "Error parsing data " + e.toString());
    }

    return jObj;
  }

  public static JSONObject getJSONfromIP(String inetAddress, int port) {
    InetAddress addr = null;
    Socket s = null;
    try {
      addr = InetAddress.getByName(inetAddress);
    } catch (UnknownHostException e) {
      Log.e("NetworkManager", "InetAddress converting failure for " + inetAddress);
    }
    try {
      s = new Socket(addr, port);
    } catch (IOException e) {
      Log.e("NetworkManager", "Socket creating IOException");
    }

    String result = "";
    JSONObject jObj = null;

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          s.getInputStream()), 8);
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println("LINE: " + line);
        sb.append(line + "\n");
      }
      reader.close();
      s.close();
      result = sb.toString();
    } catch (Exception e) {
      Log.e("NetworkManager", "Error converting result" + e.toString());
    }

    try {
      jObj = new JSONObject(result);
    } catch (JSONException e) {
      Log.e("NetworkManager", "Error parsing data " + e.toString());
    }

    return jObj;
  }

  public static class doScrape extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {
      return null;
    }

  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //    JSON
  //
  public static class FeedlistJSON{
    public static final String ARRAY_ARG = "ARRAY!";
    public static final String ITEM_ARG = "ITEM!";

    public static final ArrayList<Feed> toFeedList(JSONObject jObj) throws JSONException {
      JSONArray jsonFeedArray = jObj.getJSONArray(ARRAY_ARG);
      ArrayList<Feed> ret = new ArrayList<>();

      for(int i=0; i<jsonFeedArray.length(); i++){
        JSONObject jsonFeed = (JSONObject) jsonFeedArray.get(0);
        JSONObject item = (JSONObject) jsonFeed.get(ITEM_ARG);
        String type = item.getString(Item.JSON.TYPE_STRING);
        Item found_item = null;
        switch (type){
          case "Phone":
            found_item = new Phone().fromJSON(item);
            break;
          case "Key":
            //
            break;
          case "Dog":
            //
            break;
        }

        ret.add(SimpleFeed.fromJSON(jsonFeed, found_item));
      }

      return ret;
    }
  }
}
