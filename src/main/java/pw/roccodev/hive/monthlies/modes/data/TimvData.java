package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class TimvData extends Mode {

    public long karma, d_points, t_points, i_points;
    public String most_change;

    public TimvData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);

        karma = longDiff(current.get("karma"), seasonData.get("karma"));
        d_points = longDiff(current.get("detective"), seasonData.get("detective"));
        t_points = longDiff(current.get("traitor"), seasonData.get("traitor"));
        i_points = longDiff(current.get("innocent"), seasonData.get("innocent"));

        most_change = seasonData.get("most_points") + " -> " + current.get("most_points");


    }

    @Override
    public long compare() {
        return karma;
    }
}
