package tk.roccodev.bots.trackerbot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import tk.roccodev.bots.trackerbot.database.FirebaseStorage;
import tk.roccodev.bots.trackerbot.listener.PMListener;
import tk.roccodev.bots.trackerbot.track.Scheduler;

public class Main {



    public static void main(String[] args) {

        try {
            Bot.bot = new JDABuilder(AccountType.BOT)
                    .setToken(System.getenv("BOT_TOKEN"))
                    .addEventListener(new PMListener())
                    .buildBlocking();


            Bot.bot.getPresence().setStatus(OnlineStatus.IDLE);

            FirebaseStorage.init();
            Scheduler.init();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
