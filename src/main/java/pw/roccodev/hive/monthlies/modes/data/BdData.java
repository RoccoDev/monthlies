package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class BdData extends Mode {

    public long points, kills, deaths, victories, played, energy;

    public BdData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);
        points = longDiff("total_points", current, seasonData);
        kills = longDiff("kills", current, seasonData);
        deaths = longDiff("deaths", current, seasonData);
        victories = longDiff("batteries_charged", current, seasonData);
        played = longDiff("games_played", current, seasonData);
        energy = longDiff("energy_collected", current, seasonData);
    }

    @Override
    public long compare() {
        return points;
    }
}
