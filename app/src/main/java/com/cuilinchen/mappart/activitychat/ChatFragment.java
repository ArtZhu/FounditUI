package com.cuilinchen.mappart.activitychat;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuilinchen.mappart.FeedDetailActivity.DetailActivity;
import com.cuilinchen.mappart.R;
import com.cuilinchen.mappart.TCP.TCPClient;
import com.cuilinchen.mappart.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Art on 5/11/16.
 */
public class ChatFragment extends Fragment {
  private String addr = "10.0.0.2.";
  private int port = 8819;

  public User getLocal_user() {
    return local_user;
  }

  private User local_user, remote_user;
  private EditText input_box;
  private ListView chat_box;
  private TextView send;
  private TCPClient tcpClient;

  private ArrayList<ChatMessage> chat_records = new ArrayList<>();
  private ChatBoxAdapter chatBoxAdapter;

  public ChatFragment() {}

  public static ChatFragment newInstance(User _local_user, User _remote_user){
    ChatFragment f = new ChatFragment();
    Bundle args = new Bundle();
    args.putSerializable("LocalUser", _local_user);
    args.putSerializable("RemoteUser", _remote_user);
    f.setArguments(args);

    return f;
  }



  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.layout_chat, container, false);


    input_box = (EditText) v.findViewById(R.id.input_box);
    chat_box = (ListView) v.findViewById(R.id.chat_box);
    chat_box.setAdapter(chatBoxAdapter);
    send = (TextView) v.findViewById(R.id.button_send);

    chat_box.setDividerHeight(0);


    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //new InitTCPTask().execute();

    send.setClickable(true);
    send.bringToFront();
    send.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Toast.makeText(getActivity(), "send down:" + (tcpClient == null), Toast.LENGTH_SHORT).show();
        if (tcpClient != null) {
          String content = input_box.getText().toString();
          tcpClient.sendMessage(content);
          input_box.setText("");



          chatBoxAdapter.add(new ChatMessage(local_user, content));
          // notify the adapter that the data set has changed. This means that new message received
          // from server was added to the list
          chatBoxAdapter.notifyDataSetChanged();
          chat_box.setSelection(chatBoxAdapter.getCount() - 1);
        }
      }
    });
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    local_user = (User) getArguments().get("LocalUser");
    remote_user = (User) getArguments().get("RemoteUser");
    chatBoxAdapter = new ChatBoxAdapter(
        getActivity(), this, 0, chat_records);
  }

  @Override
  public void onPause() {
    super.onPause();
    if(tcpClient != null) {
      tcpClient.stopClient();
      tcpClient = null;
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
      new InitTCPTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    else
      new InitTCPTask().execute();
  }

  class InitTCPTask extends AsyncTask<String,String,TCPClient> {

    @Override
    protected TCPClient doInBackground(String... message) {
      //we create a TCPClient object and
      tcpClient = new TCPClient(new TCPClient.MessageReceiver() {
        @Override
        //here the messageReceived method is implemented
        public void messageReceived(String message) {
          //this method calls the onProgressUpdate
          publishProgress(message);
        }

        @Override
        public void connectionQuitted() {
          tcpClient.stopClient();
          tcpClient = null;
          Toast.makeText(getContext(), "Founder Quit", Toast.LENGTH_SHORT).show();
          ((DetailActivity) getActivity()).disableContact();
        }
      });
      tcpClient.run();
      return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
      super.onProgressUpdate(values);

      Log.e("Received", values[0]);
      //in the arrayList we add the messaged received from server
      chatBoxAdapter.add(new ChatMessage(remote_user, values[0]));
      // notify the adapter that the data set has changed. This means that new message received
      // from server was added to the list
      chatBoxAdapter.notifyDataSetChanged();
      chat_box.setSelection(chatBoxAdapter.getCount() - 1);
    }
  }
}
