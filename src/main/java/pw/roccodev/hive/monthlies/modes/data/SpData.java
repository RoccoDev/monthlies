package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class SpData extends Mode {

    public long points, eggs, blocks, played, deaths, victories;

    public SpData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);
        points = longDiff("points", current, seasonData);
        eggs = longDiff("eggs", current, seasonData);
        deaths = longDiff("deaths", current, seasonData);
        blocks = longDiff("blocks", current, seasonData);
        played = longDiff("played", current, seasonData);
        victories = played - deaths;
    }

    @Override
    public long compare() {
        return points;
    }
}
