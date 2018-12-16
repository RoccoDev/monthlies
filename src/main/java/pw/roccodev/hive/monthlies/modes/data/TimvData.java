package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class TimvData extends Mode {

    public long karma, d_points, t_points, i_points;
    public String most_change;

    public TimvData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);

        karma = longDiff("karma", current, seasonData);
        d_points = longDiff("detective", current, seasonData);
        t_points = longDiff("traitor", current, seasonData);
        i_points = longDiff("innocent", current, seasonData);

        most_change = seasonData.get("mostPoints") + " -> " + current.get("mostPoints");


    }

    @Override
    public long compare() {
        return karma;
    }
}
