package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class SkyData extends Mode {

    public long points, kills, deaths, victories, played;
    public String most_change;

    public SkyData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);

        points = longDiff("total_points", current, seasonData);
        kills = longDiff("kills", current, seasonData);
        deaths = longDiff("deaths", current, seasonData);
        victories = longDiff("victories", current, seasonData);
        played = longDiff("gamesplayed", current, seasonData);

        long seasonMost = (long) seasonData.get("most_points");
        long currentMost = (long) current.get("most_points");
        most_change = seasonMost == currentMost ? "--" : seasonMost + " -> " + currentMost;

    }

    @Override
    public long compare() {
        return points;
    }
}
