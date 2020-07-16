package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class GravData extends Mode {

    public long points, victories, played;

    public GravData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);
        points = longDiff("points", current, seasonData);
        victories = longDiff("victories", current, seasonData);
        played = longDiff("gamesplayed", current, seasonData);
    }

    @Override
    public long compare() {
        return points;
    }
}
