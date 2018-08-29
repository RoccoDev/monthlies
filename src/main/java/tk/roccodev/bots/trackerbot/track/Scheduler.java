package tk.roccodev.bots.trackerbot.track;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Scheduler {

    public static void init() throws SchedulerException {
        new Thread(() -> {
            org.quartz.Scheduler sch = null;
            try {
                sch = StdSchedulerFactory.getDefaultScheduler();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            try {
                sch.start();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

            JobDetail jobDetail = JobBuilder.newJob(TrackJob.class).withIdentity("job").build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(" 	0 0/2 * 1/1 * ? *")).build();


            try {
                sch.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        }).start();

    }

}
