package pw.roccodev.hive.monthlies.modes.data;

import org.json.simple.JSONObject;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;
import pw.roccodev.hive.monthlies.modes.Mode;

public class DrawData extends Mode {

    public long points, victories, played, correct, incorrect, skips;

    public DrawData(LeaderboardPlace current, JSONObject seasonData) {
        super(current, seasonData);
        points = longDiff("total_points", current, seasonData);
        victories = longDiff("victories", current, seasonData);
        played = longDiff("gamesplayed", current, seasonData);
        correct = longDiff("correct_guesses", current, seasonData);
        incorrect = longDiff("incorrect_guesses", current, seasonData);
        skips = longDiff("skips", current, seasonData);
    }

    @Override
    public long compare() {
        return points;
    }
}
