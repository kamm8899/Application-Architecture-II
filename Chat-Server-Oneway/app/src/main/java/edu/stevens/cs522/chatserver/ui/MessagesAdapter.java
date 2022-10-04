package edu.stevens.cs522.chatserver.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.entities.Message;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private static final String TAG = MessagesAdapter.class.getCanonicalName();

    private List<Message> messages;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     *
     * This class is used to hold the XML elements for this particular row
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //every row will have 2 textviews
        private final TextView senderView;
        private final TextView messageView;

        public ViewHolder(View view) {
            super(view);
            // define click listener for the ViewHolder's View

            senderView = (TextView) view.findViewById(R.id.header);

            messageView = (TextView) view.findViewById(R.id.message);
        }

        public void setSender(String sender) {
            senderView.setText(sender);
        }

        public void setMessage(String message) {
            messageView.setText(message);
        }
    }

    /**
     * Initialize the dataset of the Adapter
     */
    public MessagesAdapter() {
        this.messages = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // TODO set the fields of the view for the message
        //this is where we take the information in our list and we show it on
        //the Recycler View by setting the ViewHolder's XML elements with the data that should show

        //the position represents the index of the RecyclerView
        //if I'm on index 0, I should show the information for message at index 0 in the list of messages
        Message messageToShow = messages.get(position);
        //we have access to the ViewHolder for this row, which contains our textviews
        viewHolder.setSender( messageToShow.sender );
        viewHolder.setMessage( messageToShow.messageText );


    }

    // Return the size of your dataset (invoked by the layout manager)
    //NOTE: when you start a RecyclerView adapter from a template this will say return 0
    //and NO ITEMS WILL SHOW
    //The return value of this method has to reflect how many items you want to be showing
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /*
     * Invoked by live data observer.
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
