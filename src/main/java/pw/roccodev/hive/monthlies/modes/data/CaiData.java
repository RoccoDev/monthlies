package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class CaiData extends Mode {

    public long points, victories, played, captures, caught;

    public CaiData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);

        points = longDiff("points", current, seasonData);
        victories = longDiff("victories", current, seasonData);
        captures = longDiff("captures", current, seasonData);
        caught = longDiff("caught", current, seasonData);
        played = longDiff("played", current, seasonData);

    }

    @Override
    public long compare() {
        return points;
    }
}
