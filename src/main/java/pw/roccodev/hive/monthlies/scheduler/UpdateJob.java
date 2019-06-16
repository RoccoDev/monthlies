package pw.roccodev.hive.monthlies.scheduler;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.simple.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.beezig.hiveapi.wrapper.utils.json.LazyObject;
import pw.roccodev.hive.monthlies.SupportedGame;
import pw.roccodev.hive.monthlies.modes.Mode;
import pw.roccodev.hive.monthlies.utils.MergeLb;
import pw.roccodev.hive.monthlies.utils.Sort;
import tk.roccodev.hiveserver.bmont.LBs;
import tk.roccodev.hiveserver.bmont.Winstreaks;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;

public class UpdateJob implements Job {

    private static final String SEASON_URL = "https://monthlies-{g}.firebaseio.com/season.json";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for(SupportedGame g : SupportedGame.values()) {
            System.out.println("Updating leaderboard for " + g.name());
            String code = g.name().toLowerCase();
            List<LeaderboardPlace> current = MergeLb.getUpdate(g.name());
            LazyObject season;
            try {
                season = new LazyObject(null, new URL(SEASON_URL.replace("{g}", g.getDbName())));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                continue;
            }
            season.fetch();
            JSONObject seasonData = season.getInput();

            DatabaseReference ref = FirebaseDatabase.getInstance(FirebaseApp.getInstance(g.name()))
                    .getReference("monthly");

            LinkedHashMap<Mode, Long> toOrder = new LinkedHashMap<>();

            Class toInstantiate = g.getDataClass();
            Constructor constr;
            try {
                constr = toInstantiate.getConstructor(LeaderboardPlace.class, JSONObject.class);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            for(LeaderboardPlace place : current) {
                String uuid = (String) place.get("UUID");
                if(!seasonData.containsKey(uuid)) continue;
                Mode mode;
                try {
                    mode = (Mode) constr.newInstance(place, season.getJSONObject(uuid));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                toOrder.put(mode, mode.compare());
            }

            LinkedHashMap<String, Mode> toSet = Sort.sortByValueAndApplyPlace(toOrder);
            ref.setValueAsync(toSet);


        }

        // Bedwars
        try {
            LBs.updateTheTing();
            Winstreaks.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
