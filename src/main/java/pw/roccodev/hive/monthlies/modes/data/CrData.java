package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class CrData extends Mode {

    public long points, kills, deaths, victories;

    public CrData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);
        points = longDiff("points", current, seasonData);
        kills = longDiff("kills", current, seasonData);
        deaths = longDiff("deaths", current, seasonData);
        victories = longDiff("victories", current, seasonData);
    }

    @Override
    public long compare() {
        return points;
    }
}
