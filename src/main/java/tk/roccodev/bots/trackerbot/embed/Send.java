package tk.roccodev.bots.trackerbot.embed;

import net.dv8tion.jda.core.entities.MessageEmbed;
import tk.roccodev.bots.trackerbot.Bot;

public class Send {

    public static void sendMessageJoinLeave(MessageEmbed emb, long target) {
        Bot.bot.getUserById(target).openPrivateChannel()
                .queue(ch -> ch.sendMessage(emb).queue(m -> m.addReaction("\uD83D\uDD34").queue()));
    }

    public static void sendInactivity(MessageEmbed emb, long target) {
        Bot.bot.getUserById(target).openPrivateChannel()
                .queue(ch -> ch.sendMessage(emb).queue(m -> m.addReaction("➕").queue()));
    }

    public static void sendSuccesfully(MessageEmbed emb, long target) {
        Bot.bot.getUserById(target).openPrivateChannel()
                .queue(ch -> ch.sendMessage(emb).queue(m -> m.addReaction("\uD83D\uDCF0").queue()));
    }

    public static void sendList(MessageEmbed emb, long target) {
        Bot.bot.getUserById(target).openPrivateChannel().queue(ch -> ch.sendMessage(emb).queue(m -> m.addReaction("\uD83D\uDDD1").queue()));
    }

    public static void sendClear(MessageEmbed emb, long target) {
        Bot.bot.getUserById(target).openPrivateChannel().queue(ch -> ch.sendMessage(emb).queue());
    }

    public static void multiple(MessageEmbed emb, long target) {
        Bot.bot.getUserById(target).openPrivateChannel().queue(ch -> ch.sendMessage(emb).queue(m -> {
            m.addReaction("☑").queue();
            m.addReaction("❎").queue();
        }));
    }

}
