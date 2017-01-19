package com.cuilinchen.mappart.user;

import com.cuilinchen.mappart.R;

import java.io.Serializable;

/**
 * Created by Art on 5/11/16.
 */
public class User implements Serializable{
  public final String email;
  public final transient String password;
  public final transient int img;

  public static final User Martha = new User("Martha@bc.edu", "123444444", R.mipmap.userimg1);
  public static final User Bear = new User("Bear@bc.edu", "123444444", R.mipmap.userimg2);

  public User(String _username, String _password, int _img){
    email = _username;
    password = _password;
    img = _img;
  }

  public String getUserName(){
    return email.substring(0, email.indexOf("@") - 1);
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof User)){
      return false;
    }

    User u = (User) o;
    return u.email.equals(email);
  }
}
