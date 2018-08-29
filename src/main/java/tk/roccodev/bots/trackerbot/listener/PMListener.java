package tk.roccodev.bots.trackerbot.listener;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import tk.roccodev.bots.trackerbot.Bot;
import tk.roccodev.bots.trackerbot.database.DatabaseIO;
import tk.roccodev.bots.trackerbot.embed.Builder;
import tk.roccodev.bots.trackerbot.embed.Send;
import tk.roccodev.bots.trackerbot.utils.Fetcher;

public class PMListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent evt) {
        if(!evt.isFromType(ChannelType.PRIVATE)) return;
        String message = evt.getMessage() == null ? "" : evt.getMessage().getContentStripped();
        long authorId = evt.getAuthor().getIdLong();

        String[] data = message.split(" ");
        String mode = data[0].toLowerCase();
        if(data.length == 1) {
            switch(mode) {
                case "list":
                    Fetcher.sendForUser(evt.getAuthor().getIdLong());
                    break;
                case "clear":
                    DatabaseIO.clear(evt.getAuthor().getId());
                    Send.sendClear(Builder.succesfullyCleared(), evt.getAuthor().getIdLong());
                    break;
            }
        }
        else {

            String param = data[1];

            switch (mode) {
                case "add":
                    if(DatabaseIO.insert(param, authorId))
                    Send.sendSuccesfully(Builder.succesfullyAdded(param), authorId);
                    break;
                case "remove":
                    DatabaseIO.remove(param, authorId);
                    Send.sendSuccesfully(Builder.succesfullyRemoved(param), authorId);
                    break;
            }
        }
    }


    @Override
    public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent event) {
        if(Bot.bot.getSelfUser().getId().equals(event.getUser().getId())) return;
        new Thread(() -> {
            Message msg = event.getChannel().getMessageById(event.getMessageId()).complete();
            if(msg.getEmbeds().size() == 0) return;
            String footer = msg.getEmbeds().get(0).getFooter() == null ? "" : msg.getEmbeds().get(0).getFooter().getText();
            switch(event.getReactionEmote().getName()) {
                case "\uD83D\uDD34":
                    DatabaseIO.remove(footer, event.getChannel().getUser().getIdLong());
                    Send.sendSuccesfully(Builder.succesfullyRemoved(footer), event.getChannel().getUser().getIdLong());
                    break;
                case "\uD83D\uDCF0":
                    Fetcher.sendForUser(event.getChannel().getUser().getIdLong());
                    break;
                case "➕":
                    if(DatabaseIO.insert(footer, event.getChannel().getUser().getIdLong()))
                    Send.sendSuccesfully(Builder.succesfullyAdded(footer), event.getChannel().getUser().getIdLong());
                    break;
                case "\uD83D\uDDD1":
                    DatabaseIO.clear(event.getChannel().getUser().getId());
                    Send.sendClear(Builder.succesfullyCleared(), event.getChannel().getUser().getIdLong());
                    break;
                case "☑":
                    DatabaseIO.insertSkip(footer, event.getChannel().getUser().getIdLong(), true);
                    Send.sendSuccesfully(Builder.succesfullyAdded(footer), event.getChannel().getUser().getIdLong());
                    break;
                case "❎":
                    break;

            }



        }).start();

    }
}
