package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class BpData extends Mode {

    public long points, eliminations, placings, victories, played;

    public BpData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);

        points = longDiff("total_points", current, seasonData);
        eliminations = longDiff("total_eliminations", current, seasonData);
        placings = longDiff("total_placing", current, seasonData);
        victories = longDiff("victories", current, seasonData);
        played = longDiff("games_played", current, seasonData);

    }

    @Override
    public long compare() {
        return points;
    }
}
