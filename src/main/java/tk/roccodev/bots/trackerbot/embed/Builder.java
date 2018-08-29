package tk.roccodev.bots.trackerbot.embed;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.util.Date;

public class Builder {


    public static MessageEmbed userJoined(String user) {
        return new EmbedBuilder().setColor(Color.GREEN).appendDescription("**" + user + "** has logged ON!")
                .setFooter(user, null).setTimestamp(new Date().toInstant())
                .build();
    }

    public static MessageEmbed userLeft(String user) {
        return new EmbedBuilder().setColor(Color.RED).appendDescription("**" + user + "** has logged OFF!")
                .setFooter(user, null).setTimestamp(new Date().toInstant())
                .build();
    }

    public static MessageEmbed userRemovedInactive(String user) {
        return new EmbedBuilder().setColor(Color.CYAN).appendDescription("**" + user + "** has been removed from " +
                "your watchlist because they were added 3 days ago.").setFooter(user, null)
                .setTimestamp(new Date().toInstant()).build();
    }

    public static MessageEmbed userOnlineWantAdd(String user) {
        return new EmbedBuilder().setColor(Color.WHITE).appendDescription("**" + user + "** is online right now. " +
                "Do you still want to add them to your watch list?").setFooter(user, null)
                .setTimestamp(new Date().toInstant()).build();
    }

    public static MessageEmbed succesfullyAdded(String user) {
        return new EmbedBuilder().setColor(Color.GREEN)
                .appendDescription("Succesfully added **" + user + "** to your watch list.")
                .setTimestamp(new Date().toInstant())
                .build();
    }

    public static MessageEmbed succesfullyRemoved(String user) {
        return new EmbedBuilder().setColor(Color.RED)
                .appendDescription("Succesfully removed **" + user + "** from your watch list.")
                .setTimestamp(new Date().toInstant())
                .build();
    }


    public static MessageEmbed succesfullyCleared() {
        return new EmbedBuilder().setColor(Color.GREEN)
                .appendDescription("Succesfully cleared your watch list.")
                .setTimestamp(new Date().toInstant())
                .build();
    }

    public static MessageEmbed list(String description) {
        return new EmbedBuilder().setColor(Color.ORANGE)
                .appendDescription(description)
                .setAuthor("Your Watch List")
                .setTimestamp(new Date().toInstant())
                .build();
    }

    public static MessageEmbed playerNotFound() {
        return new EmbedBuilder().setColor(Color.BLUE)
                .appendDescription("Player not found.")
                .setTimestamp(new Date().toInstant()).build();
    }

}
