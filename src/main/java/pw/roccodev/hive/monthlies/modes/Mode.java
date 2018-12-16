package pw.roccodev.hive.monthlies.modes;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;

public class Mode {

    public String UUID, username;
    public int place; // Will be assigned by the worker

    public Mode(LeaderboardPlace current, JSONObject seasonData) {
        UUID = (String) current.get("UUID");
        username = (String) current.get("username");
    }

    protected long longDiff(String key, LeaderboardPlace a, JSONObject b) {
        return (long) a.get(key) - (long) b.get(key);
    }

    public long compare() { return 0; }

}
