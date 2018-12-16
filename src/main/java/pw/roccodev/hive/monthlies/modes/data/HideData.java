package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class HideData extends Mode {

    public long points, hider_kills, deaths, victories, seeker_kills;

    public HideData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);

        points = longDiff("points", current, seasonData);
        hider_kills = longDiff("hiderKills", current, seasonData);
        seeker_kills = longDiff("seekerKills", current, seasonData);
        deaths = longDiff("deaths", current, seasonData);
        victories = longDiff("victories", current, seasonData);

    }

    @Override
    public long compare() {
        return points;
    }
}
