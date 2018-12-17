package pw.roccodev.hive.monthlies.utils;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.beezig.hiveapi.wrapper.utils.json.LazyObject;
import pw.roccodev.hive.monthlies.SupportedGame;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ResetMode {

    private static final String SEASON_URL = "https://monthlies-{g}.firebaseio.com/monthly.json";

    public static void run(SupportedGame game) {

        // Upload this season's data to Google Cloud
        Bucket dataContainer = StorageClient.getInstance(FirebaseApp.getInstance(game.name())).bucket();
        long latest = 0;
        Page<Blob> otherData = dataContainer.list();
        while(otherData.hasNextPage()) latest++;
        LazyObject season;
        try {
            season = new LazyObject(null, new URL(SEASON_URL.replace("{g}", game.name().toLowerCase())));
        } catch (MalformedURLException e) {
            return;
        }
        season.fetch();
        JSONObject seasonData = season.getInput();

        dataContainer.create(++latest + "", seasonData.toJSONString().getBytes(), "application/json");

        // Reset the leaderboard
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
