package tk.roccodev.bots.trackerbot.mod;

public class ModCheck {

    public static boolean isModerator(long userId) {
        return ModConstants.communityGuild.getMemberById(userId).getRoles().contains(ModConstants.moderatorRole);
    }
}
