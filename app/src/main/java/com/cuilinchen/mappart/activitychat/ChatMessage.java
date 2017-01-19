package com.cuilinchen.mappart.activitychat;

import com.cuilinchen.mappart.user.User;

/**
 * Created by Art on 5/11/16.
 */
public class ChatMessage {
  public final User user;
  public final String content;

  public ChatMessage(User _user, String _content){
    user = _user;
    content = _content;
  }

}
