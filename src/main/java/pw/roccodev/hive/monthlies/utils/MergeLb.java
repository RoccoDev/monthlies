package pw.roccodev.hive.monthlies.utils;

import pw.roccodev.beezig.hiveapi.wrapper.game.Game;
import pw.roccodev.beezig.hiveapi.wrapper.game.leaderboard.LeaderboardPlace;

import java.util.ArrayList;
import java.util.List;

public class MergeLb {

    public static List<LeaderboardPlace> getUpdate(String game) {
        Game g = new Game(game);
        List<LeaderboardPlace> result = new ArrayList<>();

        for(int i = 0; i <= 800; i += 200) {
            result.addAll(g.getLeaderboard(i, i + 200).getPlayers());
        }

        return result;
    }

    public static List<LeaderboardPlace> getReset(String game) {
        return getUpdate(game);
    }

}
