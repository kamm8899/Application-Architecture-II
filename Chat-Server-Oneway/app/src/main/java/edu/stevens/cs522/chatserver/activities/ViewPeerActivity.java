package edu.stevens.cs522.chatserver.activities;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;
import edu.stevens.cs522.chatserver.ui.TextAdapter;
import edu.stevens.cs522.chatserver.viewmodels.PeerViewModel;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends FragmentActivity {

    public static final String TAG = ViewPeerActivity.class.getCanonicalName();

    public static final String PEER_KEY = "peer";

    private TextAdapter<Message> messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer id as intent extra");
        }

        // TODO Set the fields of the UI
        TextView userName = findViewById(R.id.view_user_name);
        TextView lastSeen = findViewById(R.id.view_timestamp);
        TextView location = findViewById(R.id.view_location);

        userName.setText( getString(R.string.view_user_name, peer.name)  );
        lastSeen.setText( getString(R.string.view_timestamp, formatTimestamp(peer.timestamp))  );
        location.setText( getString(R.string.view_location, peer.latitude,peer.longitude)  );
        // End TODO

        // Initialize the recyclerview and adapter for messages
        RecyclerView messageList = findViewById(R.id.message_list);
        messageList.setLayoutManager(new LinearLayoutManager(this));

        messagesAdapter = new TextAdapter<>(messageList);
        messageList.setAdapter(messagesAdapter);

        // TODO open the view model
        PeerViewModel peerViewModel = new PeerViewModel(getApplication());

        // TODO query the database asynchronously, and use messagesAdapter to display the result
        List<Message> messages = peerViewModel.fetchMessagesFromPeer(peer).getValue();

        //in order for the message list to update in real time we need an observer
        peerViewModel.fetchMessagesFromPeer(peer).observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                //when a new message gets added to the database
                //set the adapter to use this list of messages
                messagesAdapter.setDataset(messages);
                //notify the adapter that the data has changed and it should redraw
                messagesAdapter.notifyDataSetChanged();
            }
        });


    }

    //TODO TAKE THIS OUT, its for older android devices
    private static String formatTimestamp(Date timestamp) {
        LocalDateTime dateTime = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);

        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(timestamp);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}