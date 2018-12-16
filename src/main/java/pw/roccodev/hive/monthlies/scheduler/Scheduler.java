package pw.roccodev.hive.monthlies.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.TriggerBuilder.newTrigger;

public class Scheduler {

    public static void init() {
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

            JobDetail jobDetail = JobBuilder.newJob(ResetJob.class).withIdentity("reset").build();
            JobDetail update = JobBuilder.newJob(UpdateJob.class).withIdentity("update").build();

            Trigger trigger = newTrigger().withIdentity("trigger").startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10 1 * ?")).build();


            Trigger trigger2 = newTrigger()
                    .withIdentity("update")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(" 	0 0/5 * 1/1 * ? *"))
                    .build();

            try {
                sch.scheduleJob(jobDetail, trigger);
                sch.scheduleJob(update, trigger2);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        }).start();

    }

}
