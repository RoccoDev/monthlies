package tk.roccodev.bots.trackerbot.utils;

import tk.roccodev.bots.trackerbot.database.DatabaseIO;
import tk.roccodev.bots.trackerbot.embed.Builder;
import tk.roccodev.bots.trackerbot.embed.Send;
import tk.roccodev.bots.trackerbot.oop.Callback;
import tk.roccodev.bots.trackerbot.oop.Structure;

public class Fetcher {

    public static void sendForUser(long userId) {
        DatabaseIO.fetchAll(new Callback<Structure[]>() {
            @Override
            public void call(Structure[] param) {
                StringBuilder desc = new StringBuilder();
                for(Structure s : param) {
                    if(s.getIds().containsKey(userId)) {
                        desc.append("`").append(s.getTracked()).append("`").append("\n");
                    }
                }
                Send.sendList(Builder.list(desc.toString()), userId);
            }
        });
    }

}
