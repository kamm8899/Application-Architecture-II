package edu.stevens.cs522.chatserver.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.stevens.cs522.chatserver.databases.ChatDatabase;
import edu.stevens.cs522.chatserver.entities.Peer;

public class PeersViewModel extends AndroidViewModel {

    private ChatDatabase chatDatabase;

    private LiveData<List<Peer>> peers;

    public PeersViewModel(Application context) {
        super(context);
        chatDatabase = ChatDatabase.getInstance(context);
    }

    /*
     * TODO finish this
     */
    public LiveData<List<Peer>> fetchAllPeers() {
        //there is a list in this class that can hold all of the peers
        //but maybe we haven't created that list yet by querying the databse

        //if peers is null we have not queried the datebase yet so lets query it
        //with loadPeers90
        if (peers == null) {
            peers = loadPeers();
        }
        //at this point, whether we did it above or we did it earlier the peers list should be
        //initialized/created so we can just return it
        return peers;
    }

    private LiveData<List<Peer>> loadPeers() {
        return chatDatabase.peerDao().fetchAllPeers();
    }
    // End TODO

    @Override
    public void onCleared() {
        super.onCleared();
        chatDatabase = null;
    }
}