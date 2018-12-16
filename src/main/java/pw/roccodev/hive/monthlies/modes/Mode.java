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

    protected long longDiff(Object a, Object b) {
        return (long) a - (long) b;
    }

    public long compare() { return 0; }

}
