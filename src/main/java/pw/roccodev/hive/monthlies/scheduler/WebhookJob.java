package pw.roccodev.hive.monthlies.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pw.roccodev.hive.monthlies.utils.discord.SendWebhook;

public class WebhookJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SendWebhook.sendAlmostReset();
    }
}
