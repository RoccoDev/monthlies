package tk.roccodev.bots.trackerbot.mod;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import tk.roccodev.bots.trackerbot.Bot;

public class ModConstants {

    private static final long MODERATOR_ID = 262707342783545344L;
    private static final long GUILD_ID = 262699181620068352L;

    static Guild communityGuild;
    static Role moderatorRole;

    public static void init() {
        communityGuild = Bot.bot.getGuildById(GUILD_ID);
        moderatorRole = communityGuild.getRoleById(MODERATOR_ID);
    }

}
