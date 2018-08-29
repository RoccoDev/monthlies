package tk.roccodev.bots.trackerbot.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import tk.roccodev.bots.trackerbot.embed.Builder;
import tk.roccodev.bots.trackerbot.embed.Send;
import tk.roccodev.bots.trackerbot.oop.Callback;
import tk.roccodev.bots.trackerbot.oop.Structure;
import tk.roccodev.hiveapi.player.PlayerExtras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseIO {

    public static boolean insert(String toTrack, long author) {
        System.out.println("Adding " + toTrack + " for " + author);

        try {

            boolean online = !PlayerExtras.getRawStatus(toTrack).getStatus().equals("OFFLINE");
            if (online) {
                Send.multiple(Builder.userOnlineWantAdd(toTrack), author);
                return false;
            }

        } catch(Exception e) {
            Send.sendClear(Builder.playerNotFound(), author);
            return false;
        }
        insertSkip(toTrack, author, false);
        return true;

    }

    public static void insertSkip(String toTrack, long author, boolean online) {
        FirebaseStorage.reference.child(toTrack.toLowerCase()).child(Long.toString(author)).setValueAsync(System.currentTimeMillis());
        new Thread(() -> FirebaseStorage.reference.child(toTrack.toLowerCase()).child("online")
                .setValueAsync(online)).start();
    }

    public static void remove(String toRemove, long author) {
        System.out.println("Removing " + toRemove + " for " + author);
        FirebaseStorage.reference.child(toRemove.toLowerCase()).child(Long.toString(author)).removeValueAsync();
    }

    public static void clear(String author) {
        FirebaseStorage.reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.hasChild(author)) child.child(author).getRef().removeValueAsync();
                }
            }

            @Override
            public void onCancelled(DatabaseError ignored) {}
        });
    }

    public static void remove(String toRemove) {
        System.out.println("Removing " + toRemove);
        FirebaseStorage.reference.child(toRemove).removeValueAsync();
    }

    public static void setOnline(String toSet, boolean online) {
        try {
            System.out.println("Setting " + toSet + " as online: " + online);
            FirebaseStorage.reference.child(toSet.toLowerCase()).child("online").setValueAsync(online);
        } catch(Exception ignored) {}
    }

    public static void fetchAll(Callback<Structure[]> callback) {
        new Thread(() -> FirebaseStorage.reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Structure> tr = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    String k = child.getKey();
                    HashMap<Long, Long> l = new HashMap<>();
                    boolean wasOnline = false;
                    long createdAt = 0;
                    boolean justOnline = true;

                    for(DataSnapshot child1 : child.getChildren()) {
                        switch (child1.getKey()) {
                            case "online":
                                wasOnline = (boolean) child1.getValue();
                                break;
                            case "createdAt":
                                createdAt = (long) child1.getValue();
                                break;
                            default:
                                justOnline = false;
                                l.put(Long.parseLong(child1.getKey()), (long)child1.getValue());
                                break;
                        }
                    }

                    if(justOnline) {
                        FirebaseStorage.reference.child(k).removeValueAsync();
                        continue;
                    }

                    tr.add(new Structure(k, l, wasOnline, createdAt));
                }
                callback.call(tr.toArray(new Structure[0]));
            }

            @Override
            public void onCancelled(DatabaseError ignored) {}
        })).start();

    }
}
