package pw.roccodev.hive.monthlies.scheduler;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.SupportedGame;
import pw.roccodev.hive.monthlies.utils.MergeLb;

import java.util.HashMap;
import java.util.List;

public class ResetJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for(SupportedGame game : SupportedGame.values()) {
            // TODO Run update


            // Replace old data with the current leaderboard
            DatabaseReference ref = FirebaseDatabase.getInstance(FirebaseApp.getInstance(game.name()))
                    .getReference("season");
            List<LeaderboardPlace> current = MergeLb.getReset(game.name());
            HashMap<String, LeaderboardPlace> toSet = new HashMap<>();
            for(LeaderboardPlace place : current) {
                toSet.put((String)place.get("UUID"), place);
            }
            ref.setValueAsync(toSet);

        }
    }
}
