package tk.roccodev.bots.trackerbot.track;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import tk.roccodev.bots.trackerbot.database.DatabaseIO;
import tk.roccodev.bots.trackerbot.embed.Builder;
import tk.roccodev.bots.trackerbot.embed.Send;
import tk.roccodev.bots.trackerbot.oop.Callback;
import tk.roccodev.bots.trackerbot.oop.Structure;
import tk.roccodev.hiveapi.player.PlayerExtras;

import java.util.Map;

public class TrackJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        DatabaseIO.fetchAll(new Callback<Structure[]>() {
            @Override
            public void call(Structure[] param) {
                for(Structure s : param) {
                   boolean online = !PlayerExtras.getRawStatus(s.getTracked()).getStatus().equals("OFFLINE");

                   boolean send = online != s.wasOnline();
                   if(send) DatabaseIO.setOnline(s.getTracked(), online);

                   long now = System.currentTimeMillis();

                   for(Map.Entry<Long, Long> e : s.getIds().entrySet()) {
                       if(now - e.getValue() >= 259200000) {
                           DatabaseIO.remove(s.getTracked(), e.getKey());
                           try {
                               Send.sendInactivity(Builder.userRemovedInactive(s.getTracked()), e.getKey());
                           }
                           catch(Exception ignored) {}
                           continue;
                       }
                       if(send)
                           Send.sendMessageJoinLeave((online ? Builder.userJoined(s.getTracked())
                                   : Builder.userLeft(s.getTracked())), e.getKey());
                   }


                }
            }
        });
    }
}
