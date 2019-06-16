package pw.roccodev.hive.monthlies;

import pw.roccodev.beezig.hiveapi.wrapper.GlobalConfiguration;
import pw.roccodev.hive.monthlies.firebase.Database;
import pw.roccodev.hive.monthlies.scheduler.Scheduler;
import pw.roccodev.hive.monthlies.utils.ResetMode;

public class Main {

    public static void main(String[] args) {
        Database.initAll();
        Scheduler.init();
        System.out.println("Running");
        GlobalConfiguration.setUserAgent("Monthlies Worker/1.0");
    }


}
