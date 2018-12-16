package pw.roccodev.hive.monthlies.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pw.roccodev.hive.monthlies.SupportedGame;
import pw.roccodev.hive.monthlies.utils.ResetMode;

public class ResetJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for(SupportedGame game : SupportedGame.values()) {
            // TODO Run update


            // Replace old data with the current leaderboard
            ResetMode.run(game);

        }
    }
}
