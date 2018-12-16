package pw.roccodev.hive.monthlies.utils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.SupportedGame;

import java.util.HashMap;
import java.util.List;

public class ResetMode {

    public static void run(SupportedGame game) {
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
